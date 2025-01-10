package streams;
import org.junit.jupiter.api.Test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketTest {

    // Eingangsvariabeln
    private String host = "localhost";
    private int port = 7776;

    // Testmethode -
    @Test
    public void SocketTestMethode() throws Exception {

        // 1. Stellen Sie eine TCP Verbindung zu diesem Prozess  - Socket her.
        Socket s1 = new Socket(host, port);


        // OutputStream für das Senden von Daten + Verbindung zum erstellen Socket
        OutputStream raus = s1.getOutputStream();
        // 2. Senden Sie ein beliebiges Byte (z.B. 100).
        int ausgabe = 150;
        raus.write(ausgabe);


        // InputStream zum Empfangen von Daten.
        InputStream rein = s1.getInputStream();

        // 3. Lesen Sie das Byte, das Sie zurückbekommen.
        byte[] readBuffer = new byte[1];
        rein.read(readBuffer);


        // Konvertiere das gelesene Byte in einen int-Wert, um den numerischen Wert zu erhalten
        int empfangenerWert = readBuffer[0] & 0xFF; // & 0xFF stellt sicher, dass das Byte als vorzeichenloser Wert interpretiert wird


        StringBuilder sb = new StringBuilder();
        sb.append("wrote: ");
        sb.append(ausgabe);
        sb.append(" | read: ");
        sb.append(empfangenerWert); // Hier den numerischen Wert anzeigen
        System.out.println("Server erhöhte sehr warscheinlich den Wert um 1");
        System.out.println(sb.toString());


        /* 4. Ein Prozess liest das Byte und erhöht es um eins. Prüfen Sie, ob das stimmt.
        int empfangenerWert = readBuffer[0];
        int erwarteterWert = ausgabe + 1;
        if (empfangenerWert == erwarteterWert) {
            System.out.println("Erwarteter Wert empfangen: " + empfangenerWert);
        } else {
            System.out.println("Falscher Wert empfangen: " + empfangenerWert);
        }
        */

        // Schließen der Streams und Socket-Verbindung.
        raus.close();
        rein.close();
        s1.close();


    }

}
