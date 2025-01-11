package sfs_simpleFileServer;

import utils.MySerialization;

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

        // GET request succeeded, save file
        if (commandFromServer == 1) this.handlePUT(fileName);

        // GET request did not succeed, output ERROR received from server
        if (commandFromServer == 2) this.handleERROR();

    }
    public void handlePUT(String fileName) throws IOException {
        MySerialization ms = new MySerialization();
        ms.deserializeFile(is, rootDirectory + fileName);
    }

    public void handleERROR() throws IOException {
        DataInputStream dis = new DataInputStream(is);
        int errorCode = dis.readInt();
        String errorMessage = dis.readUTF();
        System.out.println("error code received from server: " + errorCode);
        System.out.println("error message received from server: " + errorMessage);
    }


    private void writeHeader(DataOutputStream dos, byte command, String fileName) throws IOException {
        dos.writeByte(VERSION_NUMBER);
        dos.writeByte(command);
        dos.writeUTF(fileName);
    }

    public void putFile(String putFileName) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        File file = new File(rootDirectory + putFileName);

        // write PUT_PDU
        this.writeHeader(dos, PUT_PDU, putFileName);  // write header
        MySerialization ms = new MySerialization();
        ms.serializeFile(file, os);  // write file

        // read and output answer from server
        DataInputStream dis = new DataInputStream(is);
        byte versionFromServer = dis.readByte();
        System.out.println("version received from server:" + versionFromServer);
        byte commandFromServer = dis.readByte();
        System.out.println("command received from server:" + commandFromServer);
        String fileNameFromServer = dis.readUTF();
        System.out.println("file name received from server: " + fileNameFromServer);

        if (commandFromServer == OK_PDU) this.handleOK();

        if (commandFromServer == ERROR_PDU) this.handleERROR();
    }


    public void handleOK() throws IOException {
        System.out.println("request successful");
    }



}
