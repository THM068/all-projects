package keepass

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.EntryBuilder;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.KeePassFile

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths

public class KeePassLoadFromFile {

    /**
     * Adds secrets stored as text in the format of:
     *   key:password
     * into specified keepass file as the specified group
     */

     static void main(String[] args) throws IOException {

        final String fileToLoad = args[0];
        final String targetKeePassFile = args[1];
        final String targetKeePassPassword = args[2];
        final String targetGroup = args[3];
        final boolean dryRun = Boolean.parseBoolean(args[4]);

        final KeePassLoadFromFile keePassLoadFromFile = new KeePassLoadFromFile();

        final KeePassCopy.KeePassIO targetIO = new KeePassLoadFromFile.DefaultKeePassIO(targetKeePassFile, targetKeePassPassword);

        final KeePassFile keePassFile = targetIO.load();

        keePassLoadFromFile.loadEntriesFromFile(fileToLoad, keePassFile, targetGroup);

        if (!dryRun){
            targetIO.save(keePassFile);
        }
    }

    public void loadEntriesFromFile(final String fileToLoad, final KeePassFile targetKeePassFile, final String targetGroup) throws IOException {

        final List<Entry> entriesToAdd = parseEntriesFromFile(fileToLoad);
        final Group groupToPutEntries = findGroupFromUrl(targetKeePassFile.getRoot().getGroups().get(0), targetGroup).get();
        if (groupToPutEntries.getName().equalsIgnoreCase("root")){
            if (targetGroup.equalsIgnoreCase("root")){

            }else {
                throw new RuntimeException("Unable to find group");
            }
        };
        addEntitiesToGroup(groupToPutEntries, entriesToAdd);
    }

    private void addEntitiesToGroup(Group group, List<Entry> entries){
        for (final Entry entry : entries){
            if (existsInGroup(group, entry.getTitle())){
                Entry existingEntry = group.getEntryByTitle(entry.getTitle());
                Entry newEntry = new EntryBuilder(entry.getTitle())
                        .password(entry.getPassword())
                        .username(entry.getUsername())
                        .build();
                group.getEntries().add(newEntry);
                group.getEntries().remove(existingEntry);
            }else{
                group.getEntries().add(entry);
            }
        }
    }

    private boolean existsInGroup(final Group group, final String title){
        return Optional.ofNullable(group.getEntryByTitle(title)).isPresent();
    }

    private Optional<Group> findGroupFromUrl(final Group rootGroup, final String name){

        final String[] segments = name.split("/");
        Group lastGroup = rootGroup;
        for (final String segment : segments){

            Optional<Group> group = findGroup(lastGroup, segment);
            if (group.isPresent()) {
                lastGroup = group.get();
            }else{
                return Optional.of(lastGroup);
            }
        }

        return Optional.of(lastGroup);
    }

    private Optional<Group> findGroup(final Group theGroup, final String name){
        for(final Group group : theGroup.getGroups()){
            if (group.getName().equals(name)){
                return Optional.of(group);
            }
        }
        return Optional.empty();
    }

    private List<Entry> parseEntriesFromFile(final String fileToLoad) throws IOException {
        final Path path = Paths.get(fileToLoad);
        final List<String> lines = Files.readAllLines(path);
        final List<Entry> entries = new ArrayList<>();
        for (String line : lines){
            final String[] segments = line.split(":");
            final String key = segments[0];
            final String password = segments[1];
            entries.add(
                    new EntryBuilder(key).username(key.trim()).password(password.trim()).build()
            );
        }
        return entries;
    }

    private static class DefaultKeePassIO implements KeePassCopy.KeePassIO {

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
}
