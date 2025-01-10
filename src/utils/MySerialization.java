package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * as Ziel des Codes ist zu prüfen, ob ein int-Array
 * nach der Serialisierung und Deserialisierung unverändert bleibt
 */
public class MySerialization {

    // liest einen Stream
    // empfangende Array wird for each Wert im Array ein OutputStream erzeugt
    public void serialize(int[] intArray, OutputStream os) throws IOException {
        // wrappt den os in ein DataOutputStream um primitive Datentypen (int) direkt zu schreiben
        DataOutputStream dos = new DataOutputStream(os);
        // schreibt die Länge des Array in den OutputStream
        // , damit deserialize weiß wie viele Stream gelesen werden müssen
        dos.writeInt(intArray.length);
        //iteriert über jedes Element im Array sample und schreibt es nacheinaner in den Stream
        for (int intToSend : intArray) {
            dos.writeInt(intToSend);
        }
    }

    public int[] deserialize(InputStream is) throws IOException {
        // wrappt den os in ein DataOutputStream um primitive Datentypen (int) direkt zu schreiben
        DataInputStream dis = new DataInputStream(is);
        int length = dis.readInt(); // hier wird die länge gelesen aus der Methide serialize bei "dos.writeInt(intArray.length);"
        int[] receivedInts = new int[length]; // erstellt das Array anhand der Länge
        // iteriert über den Stream und füllt das Array mit den gelesenen Werten
        for (int i = 0; i < receivedInts.length; i++) {
            receivedInts[i] = dis.readInt();
        }
        return receivedInts;
    }

    public void serializeFile(File file, OutputStream os) throws IOException {

        // InputStream to read from file
        InputStream is = new FileInputStream(file);

        // write file length to server
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeLong(file.length());
        // System.out.println("client sent: " + file.length());

        // read content from file and write it to server
        for (long i = 0; i < file.length(); i++) {
            dos.write(is.read());
        }
    }
    public File deserializeFile(InputStream is, String fileName) throws IOException {

        // read file length
        DataInputStream dis = new DataInputStream(is);
        long fileLength = dis.readLong();
        // System.out.println("file length from server: " + fileLength);


        // file and FileOutputStream to write received data to file
        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        // read from client and write to file
        for (long i = 0; i < fileLength; i++) {
            fos.write(dis.read());
        }

        return file;
    }



}
