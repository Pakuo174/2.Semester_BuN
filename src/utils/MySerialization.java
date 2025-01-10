package utils;

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

    /**
     * Diese Methode dient dazu, eine Datei und einen OutputStream entgegenzunehmen.
     * <p>
     * Es wird:
     * 1. Die Länge der Datei mit einem DataOutputStream gesendet.
     * 2. Der Inhalt der Datei Byte für Byte (oder Zeichen für Zeichen) über den OutputStream gesendet.
     *
     * @param file die Datei, die serialisiert wird
     * @param os   der OutputStream, in den die Daten geschrieben werden
     * @return
     * @throws IOException falls ein I/O-Fehler auftritt
     */
    public void serializeFile(File file, OutputStream os) throws IOException {

        // beziehen von den Bytes aus der file
        InputStream is = new FileInputStream(file);

        // stream erstellen - länge der file zu schreiben + Inhalt
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeLong(file.length());

        //read file byte for byte (letter for letter) and send back to server
        for (int i = 0; i < file.length(); i++){
            dos.write(is.read());
        }

    }

    /**
     * Diese Methode dient dazu, einen InputStream und einen Dateinamen entgegenzunehmen und eine Datei zu deserialisieren.
     *
     * Es wird:
     * 1. Die Länge der Datei gelesen, damit die Methode weiß, wie viele Bytes sie lesen muss.
     * 2. In einer Schleife wird Byte für Byte aus dem InputStream (dis) gelesen und in eine neue Datei geschrieben.
     * 3. Ein FileOutputStream (fos) wird verwendet, um die Daten in die Zieldatei zu schreiben.
     *
     * @param is Der InputStream, der die zu deserialisierenden Daten enthält.
     * @param filename Der Name der Zieldatei, die erstellt wird.
     * @return Die erstellte Datei.
     * @throws IOException Falls ein I/O-Fehler auftritt.
     */
    public File deserializeFile(InputStream is, String filename) throws IOException {

        DataInputStream dis = new DataInputStream(is);
        long fileLength = dis.readLong();

        // Zieldatei erstellen
        File file = new File(filename);

        FileOutputStream fos = new FileOutputStream(file);

        // Byte-für-Byte aus dem InputStream lesen und in die Datei schreiben
        for (int i = 0; i < fileLength; i++){
            fos.write(dis.read());
        }

        return file;
    }








}
