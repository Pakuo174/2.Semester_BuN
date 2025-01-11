import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sfs_simpleFileServer.SimpleFileServerClient;

import java.io.File;
import java.io.IOException;
import java.net.Socket;



public class testSFS {

    private static String GET_FILE_NAME = "";

    @Test
    public void testGet() throws IOException {
        String rootDirName = "tests/test_files_client/";

        Socket socket = new Socket("localhost", 4444);
        SimpleFileServerClient sfsClient = new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream(), rootDirName);

        sfsClient.getFile(GET_FILE_NAME);
        File file = new File(rootDirName + "/" + GET_FILE_NAME);
        Assertions.assertTrue((file.exists()));

    }

    @Test
    public void testGetFileDoesNotExist() throws IOException {
        // Verbindung mit dem Server
        Socket socket = new Socket("localhost", 4444);

        // Erstelle eine Instanz von SimpleFileServerClient
        SimpleFileServerClient sfsClient =
                new SimpleFileServerClient(socket.getInputStream(), socket.getOutputStream(), "tests/test_files_client/");

        // Rufe das rootDirectory aus der Instanz ab
        String rootDirName = sfsClient.getRootDir();

        // Datei anfordern, die nicht existiert
        String notExistingFileName = "nonexistent.txt";
        sfsClient.getFile(notExistingFileName);

        // Prüfen, ob die Datei nicht existiert
        File file = new File(rootDirName + "/" + notExistingFileName);
        Assertions.assertFalse(file.exists(), "Die Datei sollte nicht existieren.");
    }


}
