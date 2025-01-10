package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * dient als Protokollmaschine welche den empfangenden Byte liest und wieder zurück sendet
 *
 */
public class EchoServer implements ConnectionHandler {
    @Override
    public void handleConnection(InputStream is, OutputStream os) throws IOException {
        os.write(is.read());
        // close (layer 4, namely TCP) connection
        os.close(); // TCP signalisiert dem anderen Prozess, dass nichts mehr kommt.
        is.close(); // dem OS wird gesagt, dass man nichts mehr empfangen will.
    }
}