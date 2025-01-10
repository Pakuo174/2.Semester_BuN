package streams;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamTests {

    @Test
    public void writeAndRead() throws Exception {
        String filename = "testFile.txt";
        OutputStream os = new FileOutputStream(filename);

        //write
        String someText = "hallo";
        os.write(someText.getBytes());

        // read
        InputStream is = new FileInputStream(filename);
        byte [] readBuffer = new byte[100];
        is.read(readBuffer);

        String readString = new String(readBuffer);
        StringBuilder sb = new StringBuilder();
        sb.append("wrote: ");
        sb.append(someText);
        sb.append(" | read: ");
        sb.append(readString);
        System.out.println(sb.toString());

        //Assertions.assertTrue(readString.equals(someText));
        readString = readString.substring(0, someText.length());
        System.out.println(readString);



    }
}
