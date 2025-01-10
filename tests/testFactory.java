import org.junit.Test;
import tcp.ConnectionFactory;
import tcp.DataProtocolStreamServer;
import tcp.EchoServer;
import tcp.IterativeProtocolServer;

import java.io.*;
import java.net.Socket;

public class testFactory {

    @Test
    public void runEchoEngineTests() throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory(new EchoServer());
        System.out.println("going to accept new connections");
        connectionFactory.acceptNewConnection();

    }


    @Test
    public void testConnectionFactory() throws IOException {

        Socket c1 = new Socket("localhost", 7777);

        // senden von Socket aus
        byte byte2sent = 42;
        c1.getOutputStream().write(byte2sent);
        System.out.println("sent" + byte2sent);

        // lesen vom empfangenden byte
        int readValue = c1.getInputStream().read();
        System.out.println("read: " + readValue);
        // konvertieren zu byte - Casting
        byte readByte = (byte) readValue;

    }

    /**
     * Prozess A bietet einen offenen Port an
     * @throws IOException
     */
    @Test
    public void ServerPort() throws IOException {
        int port = 7777;
        ConnectionFactory c1 = new ConnectionFactory(port, new IterativeProtocolServer());
        System.out.println("Port wurde geöffnet " + port);
             c1.acceptNewConnection();
    }
    @Test
    public void IterativeClientSocket() throws IOException {
        Socket s1 = new Socket("localhost",7777);

        OutputStream raus = s1.getOutputStream();
        InputStream rein = s1.getInputStream();

        int valueToSend = 1;


        for (int i = 0; i < 10; i++){

            raus.write(valueToSend);
            System.out.println("Wert wurde gesendet " + valueToSend);
            int receivedValue = rein.read();
            System.out.println("Wert wurde empfangen " + receivedValue);
            int incrementedValue = receivedValue +1;
            valueToSend = receivedValue +1;
        }


    }

    /**
     * hier beginnt die Aufgabe 2.5 mit DataStreams
     * Serverport wird geöffnet und es  beim Test DataStreamClient werden LONG, DOUBLE und UTF-String Werte gesendet sowie empfangen
     * @throws IOException
     */

    @Test
    public void DataProtocolServerPort() throws IOException {
        ConnectionFactory c1 = new ConnectionFactory(7777, new DataProtocolStreamServer());
        c1.acceptNewConnection();

    }

    @Test
    public void CLientSocketDataProtocl() throws IOException {
        Socket s1 = new Socket("localhost",7777);

        DataOutputStream dos = new DataOutputStream(s1.getOutputStream());
        DataInputStream dis = new DataInputStream(s1.getInputStream());

        //Values to send
        long longValue = (long )2520.4;
        double doubleValue = 5.5;
        String stringValue = "kekekek";


        dos.writeLong(longValue);
        dos.writeDouble(doubleValue);
        dos.writeUTF(stringValue);

        long RlongValue = dis.readLong();
        double RdoubleValue = dis.readDouble();
        String RstringValue = dis.readUTF();

        System.out.println("Prozess liest ein long Wert:  " + RlongValue );
        System.out.println("Prozess liest ein Double Wert:  " + RdoubleValue );
        System.out.println("Prozess liest ein UTF-String Wert:  " + RstringValue );


    }





}







