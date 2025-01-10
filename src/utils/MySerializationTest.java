package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
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




}
