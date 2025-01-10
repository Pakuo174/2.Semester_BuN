package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IterativeProtocolServer implements ConnectionHandler {

    /**
     * Prozess A lese ein byte, inkrementrie und sende es zurück
     * @param is InputStream des Clients
     * @param os OutputStream des Clients
     * @throws IOException
     */
    @Override
    public void handleConnection(InputStream is, OutputStream os) throws IOException {
        int iteration= 0;

       while (iteration < 10){
            int receivedValue = is.read();
            System.out.println("Byte wurde empfangen" );
            int incrementedValue = receivedValue + 1;
            System.out.println("Server empfing: " + receivedValue + ", inkrementiert zu: " + incrementedValue);
            os.write(incrementedValue);
            iteration ++;
        }




    }
}
