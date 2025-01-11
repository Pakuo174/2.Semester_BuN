package sfs_simpleFileServer;

import java.io.*;

public class SimpleFileServerClient {
    private InputStream is; // vom Server emfpangen
    private OutputStream os; // zum Server senden
    final byte VERSION_NUMBER = 1;
    public String rootDirectory  = "tests/test_files_client/";

    // PDU Typen von 0x00 bis 0x03
    final byte GET_PDU = 0x00;
    final byte PUT_PDU = 0x01;
    final byte ERROR_PDU = 0x02;
    final byte OK_PDU = 0x03;

    public SimpleFileServerClient(InputStream is, OutputStream os, String rootDirectory) throws IOException {
        this.is = is;
        this.os = os;
        this.rootDirectory = rootDirectory;
    }

    public String getRootDir (){
        return rootDirectory;
    }

    public void getFile(String fileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);

        // write GET_PDU
        this.writeHeader(dos, GET_PDU, fileName);

        // read header from server and output it
        DataInputStream dis = new DataInputStream(is);
        int versionFromServer = dis.readByte();
        System.out.println("version received from server: " + versionFromServer);
        int commandFromServer = dis.readByte();
        System.out.println("command received from server: " + commandFromServer);
        String fileNameFromServer = dis.readUTF();
        System.out.println("requested file name received from server: " + fileNameFromServer);
    }

    private void writeHeader(DataOutputStream dos, byte command, String fileName) throws IOException {
        dos.writeByte(VERSION_NUMBER);
        dos.writeByte(command);
        dos.writeUTF(fileName);
    }

    public void putFile(String fileName){


    }


}
