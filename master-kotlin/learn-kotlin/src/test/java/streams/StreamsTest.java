package streams;

import org.junit.jupiter.api.Test;

import java.io.*;

public class StreamsTest {
   private static final int BUFFER = 4096;
   private  static final String FILE_PATH = "/Users/tma24/private_projects/master-kotlin/learn-kotlin/src/image.jpeg";
   private  static final String DEST_FILE_PATH = "/Users/tma24/private_projects/master-kotlin/learn-kotlin/src/image_out.jpeg";

    @Test
    public void testImageCoby() {
        try (
            InputStream is = new FileInputStream(FILE_PATH);
            OutputStream os = new FileOutputStream(DEST_FILE_PATH);
        ){
            byte [] buffer = new byte[BUFFER];
            int readPart = -1;

            while ( (readPart = is.read(buffer)) != -1 ) {
                os.write(buffer, 0, readPart);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
