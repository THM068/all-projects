package keepass;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.*;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class KeePassCopy {

    private boolean overwrite;
    private boolean dryRun;
    private boolean create;
    private final KeePassIO io;

    public KeePassCopy(boolean overwrite, boolean dryRun, boolean create, KeePassIO io) {
        this.overwrite = overwrite;
        this.dryRun = dryRun;
        this.create = create;
        this.io = io;
    }

    public KeePassCopy(KeePassIO io) {
        this(false, false, false, io);
    }

    public void copy(String source, String destination) {
        KeePassFile keePassFile = io.load();
        copy(source, destination, keePassFile);
    }

    private void copy(String source, String destination, KeePassFile keePassFile) {

        Group sourceGroup = findGroup(keePassFile, source);
        Group destGroup = findGroup(keePassFile, destination);

        Set<String> destinationEntries = destGroup.getEntries().stream()
                .map(Entry::getTitle)
                .collect(Collectors.toSet());

        Set<String> sourceEntries = sourceGroup.getEntries().stream()
                .map(Entry::getTitle)
                .collect(Collectors.toSet());

        Map<String, Entry> clashingEntries = destGroup.getEntries().stream()
                .filter(e -> sourceEntries.contains(e.getTitle()))
                .collect(Collectors.toMap(Entry::getTitle, e -> e));

        for (Entry entry : sourceGroup.getEntries()) {

            boolean skip = !overwrite && destinationEntries.contains(entry.getTitle());
            boolean entryDoesNotClash = !clashingEntries.containsKey(entry.getTitle());

            if(entryDoesNotClash) {
                writeToStdOut(entry, Operation.WRITE, source, destination);
            } else if(overwrite) {
                destGroup.getEntries().remove(clashingEntries.get(entry.getTitle()));
                writeToStdOut(entry, Operation.OVERWRITE, source, destination);
            }

            if (skip) {
                writeToStdOut(entry, Operation.SKIP, source, destination);
                continue;
            }

            Entry clone = new EntryBuilder(entry.getTitle())
                    .password(entry.getPassword())
                    .username(entry.getUsername())
                    .notes(entry.getNotes())
                    .build();

            destGroup.getEntries().add(clone);
        }

        if (!dryRun) {
            io.save(keePassFile);
        }
    }

    private void writeToStdOut(Entry entry, Operation operation, String source, String destination) {
        writeToStdOut(operation,
                String.format("%s/%s", source, entry.getTitle()),
                String.format("%s/%s", destination, entry.getTitle()));
    }

    private void writeToStdOut(Operation operation, String source, String destination) {
        System.out.println(
                String.format("%s [ %s ] %s %s %s",
                        dryRun ? "[DRY RUN]" : "",
                        operation.getOp(),
                        source,
                        "->",
                        destination));
    }

    private Group findGroup(KeePassFile keePassFile, String path) {
        String[] pathSegments = path.split("/");

        List<Group> groupsAtCurrentLevel = keePassFile.getTopGroups();
        Group selected = null;

        StringBuilder subPath = new StringBuilder();

        for (String segment : pathSegments) {
            selected = null;

            for (Group currentLevelGroup : groupsAtCurrentLevel) {
                if (currentLevelGroup.getName().equalsIgnoreCase(segment)) {
                    selected = currentLevelGroup;
                }
            }

            if (selected == null) {
                if (create) {
                    writeToStdOut(Operation.CREATE_FOLDER, subPath.toString(), segment);
                    selected = new GroupBuilder(segment).build();
                    groupsAtCurrentLevel.add(selected);
                } else {
                    throw new RuntimeException("Group " + segment + " not found");
                }
            }

            groupsAtCurrentLevel = selected.getGroups();
            subPath.append(segment).append("/");
        }

        return selected;
    }

    @Getter
    private enum Operation {

        WRITE("▷"),
        OVERWRITE("▶"),
        SKIP("⏎"),
        CREATE_FOLDER("+");

        private final String op;

        Operation(String op) {
            this.op = op;
        }
    }

    interface KeePassIO {
        void save(KeePassFile file);
        KeePassFile load();
    }

    private static class DefaultKeePassIO implements KeePassIO {

        private final String path;
        private final String password;

        DefaultKeePassIO(String path, String password) {
            this.path = path;
            this.password = password;
        }

        @Override
        public void save(KeePassFile file) {
            KeePassDatabase.write(file, password, path);
        }

        @Override
        public KeePassFile load() {
            KeePassDatabase database = KeePassDatabase.getInstance(new File(path));
            return database.openDatabase(password);
        }
    }

    public static void main(String[] args) {

        Options options = new Options();

        Option overwrite = new Option("o", "overwrite", false, "Overwrite in destination");
        Option dryRun = new Option("d", "dry-run", false, "Dry run, printing which entries will be copied.");
        Option create = new Option("c", "create", false, "Create destination group structure");

        overwrite.setRequired(false);
        dryRun.setRequired(false);
        create.setRequired(false);

        options.addOption(overwrite);
        options.addOption(dryRun);
        options.addOption(create);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cli = null;

        try {
            cli = parser.parse(options, args);

            if (cli.getArgs().length != 3) {
                throw new ParseException("Please provide exactly three inline arguments - the fully qualified path " +
                        "to the KDBX file, and the source and destination groups.");
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());

            formatter.printHelp("kcp <database.kdbx> <path/to/src> <path/to/dest>", options);
            System.out.println("\noutput key:\n\t+\tNew Group\n\t▷\tWrite\n\t▶\tOverwrite\n\t⏎\tSkip\n");
            System.exit(1);
        }

        String keePassXPassword = inputKeePassXPassword();

        KeePassIO io = new DefaultKeePassIO(cli.getArgs()[0], keePassXPassword);

        KeePassCopy copy = new KeePassCopy(cli.hasOption('o'), cli.hasOption('d'), cli.hasOption('c'), io);

        String source = cli.getArgs()[1];
        String destination = cli.getArgs()[2];
        copy.copy(source, destination);
    }

    private static String inputKeePassXPassword(){
        System.out.println("KeePassX password: ");
        String keePassXPassword = String.valueOf(System.console().readPassword());
        return keePassXPassword;
    }

}
