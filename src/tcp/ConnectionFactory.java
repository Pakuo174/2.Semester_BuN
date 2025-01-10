package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Connection Factory dient zum erstellen von Instanzen der Protokollserver (ServerSocket)
 */
public class ConnectionFactory {

    public static final int DEFAULT_PORT = 7777;
    private final int port;
    private final ConnectionHandler connectionHandler;

    public ConnectionFactory(ConnectionHandler connectionHandler) {
        this.port = DEFAULT_PORT;
        this.connectionHandler = connectionHandler;
    }

    public ConnectionFactory(int port, ConnectionHandler connectionHandler) {
        this.port = port;
        this.connectionHandler = connectionHandler;
    }


    /**
     * Erstellt einen ServerSocket, akzeptiert eingehende Verbindungen und übergibt die Streams
     *   (Input und Output) an den ConnectionHandler.
     *    Ablauf:
     *      * 1. ServerSocket wird auf dem spezifizierten Port erstellt.
     *      * 2. Wartet blockierend auf eine eingehende Verbindung.
     *      * 3. Nach erfolgreicher Verbindung werden der InputStream und OutputStream des Clients
     *      *    an den ConnectionHandler übergeben.
     *
     *
     * @throws IOException
     */
    public void acceptNewConnection() throws IOException {
        ServerSocket srvSocket = new ServerSocket(this.port);
        System.out.println("Server gestartet und wartet auf Verbindungen...");


            Socket newConnection = srvSocket.accept(); // accept Methode besitzt eine endlos schleife
            System.out.println("Neue Verbindung akzeptiert.");
            this.connectionHandler.handleConnection(
                    newConnection.getInputStream(),
                    newConnection.getOutputStream());

    }


}


