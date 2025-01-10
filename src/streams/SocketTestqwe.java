package streams;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTestqwe {

    // Eingangsvariabeln
    //private String host = "localhost";
    private int port = 7777;

    // Testmethode
    @Test
    public void SocketTestMethode() throws Exception {
        // ServerSocket der am Port 7777 lauscht
        try (ServerSocket srvSocket = new ServerSocket(port)) {
            System.out.println("Server gestartet und wartet auf Verbindungen...");

            // Unendlich-Schleife, um kontinuierlich Verbindungen zu akzeptieren (bis zum Abbruch)
            while (true) {
                try (Socket clientSocket = srvSocket.accept()) {
                    System.out.println("Neue Verbindung akzeptiert.");

                    // Input und OutputStream des Cliets in Variable packen
                    InputStream in = clientSocket.getInputStream();
                    OutputStream out = clientSocket.getOutputStream();

                    // Ein Byte lesen vom InputStream
                    int receivedByte = in.read();
                    if (receivedByte != -1) {
                        System.out.println("Empfangenes Byte: " + receivedByte);

                        // Byte inkrementieren
                        int incrementedByte = (receivedByte + 1) & 0xFF; // Modulo 256, um innerhalb der Byte-Grenzen zu bleiben
                        System.out.println("Inkrementiertes Byte: " + incrementedByte);

                        // Byte zurücksenden
                        out.write(incrementedByte);
                        out.flush();
                        System.out.println("Byte zurückgesendet.");
                    }
                } catch (Exception e) {
                    System.err.println("Fehler bei der Verarbeitung der Verbindung: " + e.getMessage());
                }
            }
        }
    }
}
