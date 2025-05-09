package keepass;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;



public class ReadEntriesFromKeepass {
    KeePassFile database = KeePassDatabase
            .getInstance("C:\\Users\\sdkca\\Documents\\Database.kdbx")
            .openDatabase("12345");
}