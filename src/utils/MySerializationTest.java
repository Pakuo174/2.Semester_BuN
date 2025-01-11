package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class MySerializationTest {

    @Test
    public void arrayTest() throws IOException {
        // sample wird über ByteArrayOutputStream geschrieben
        MySerialization ms = new MySerialization();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int[] sample = new int[]{1,2,3,4};
        //übergibt das Array "sample" und den OutputStream an die Methode serialize
        ms.serialize(sample, os);
        // Konvertierung des Inhalt in ein byte-array
        byte[] serializedData = os.toByteArray();
        //Stream simuliert den Empfang der Daten
        InputStream is = new ByteArrayInputStream(serializedData);

        // übergibt den InputStream an die Methode deserialize
        int[] result = ms.deserialize(is);
        // Prüfung ob Arrays gleich sind 
        Assertions.assertArrayEquals(sample, result);
    }
    @Test
    public void fileTest0() throws IOException {
        // Testdaten und Dateinamen
        String sampleData = "TestContent";
        String fileNameSource = "sourceFile.txt"; // die zu serialisierende Datei

        // 1. Quelle-Datei erstellen
        DataOutputStream daos = new DataOutputStream(new FileOutputStream(fileNameSource));
        daos.writeUTF(sampleData);
        daos.close();

        // Datei-Inhalt auslesen und ausgeben
        DataInputStream dais = new DataInputStream(new FileInputStream(fileNameSource));
        String fileContent = dais.readUTF(); // Liest den geschriebenen String
        dais.close();

        // Ausgabe in der Konsole
        System.out.println("Inhalt der Datei '" + fileNameSource + "': " + fileContent);

        // Optional: Verifikation des Inhalts
        Assertions.assertEquals(sampleData, fileContent, "Der Inhalt der Datei stimmt nicht überein.");
    }




    @Test
    public void fileTest1() throws IOException {
        String sampleData = "TestContent";
        String fileNameSource = "sourceFile.txt";
        String fileNameTarget = "targetFile.txt";

        // fill file
        DataOutputStream daos = new DataOutputStream(new FileOutputStream(fileNameSource));
        daos.writeUTF(sampleData);

        // Datei-Objekt erstellen und ich sourcefile schreiben
        File file = new File(fileNameSource);

        // Erstelle eine Instanz deiner Klasse mit den Methoden
        MySerialization ms = new MySerialization();

        // Serialisiere die Datei in einen OutputStream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ms.serializeFile(file,bos);

        // Deserialisiere die Daten aus dem OutputStream
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ms.deserializeFile(bis,fileNameTarget);

        Assertions.assertEquals(fileNameSource.length(), fileNameTarget.length());

    }

    // Die Datenübertragung erfolgt vollständig als Byte-Stream.
    // Dies ist die Standardweise, wie Sockets Daten senden und empfangen.
    @Test
    public void testServerSide() throws IOException {

        ServerSocket server = new ServerSocket(7777);
        Socket socket = server.accept();

        // empfange die Bytes des Outputstreams
        InputStream is = socket.getInputStream();

        // create empty file
        DataOutputStream daos = new DataOutputStream(new FileOutputStream("targetFile.txt"));

        MySerialization ms = new MySerialization();
        ms.deserializeFile(is, "targetFile.txt");

        socket.close();
    }

    // Die Datenübertragung erfolgt vollständig als Byte-Stream.
    // Dies ist die Standardweise, wie Sockets Daten senden und empfangen.
    @Test
    public void testClientSide() throws IOException {
        String sampleData = "TestContent6666";
        String fileNameSource = "sourceFile.txt";

        // fill file
        DataOutputStream daos = new DataOutputStream(new FileOutputStream(fileNameSource));
        daos.writeUTF(sampleData);

        // file exists - lets stream it
        File file = new File(fileNameSource);
        MySerialization ms = new MySerialization();

        Socket socket = new Socket("localhost", 7777);
        OutputStream os = socket.getOutputStream();
        ms.serializeFile(file, os);

        socket.close();
    }






}
