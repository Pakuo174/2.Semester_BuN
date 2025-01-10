package tcp;

import java.io.*;

public class DataProtocolStreamServer implements ConnectionHandler {


    @Override
    public void handleConnection(InputStream is, OutputStream os) throws IOException {

        DataOutputStream dos = new DataOutputStream(os);
        DataInputStream dis = new DataInputStream(is);

            long LongValue = dis.readLong();
            double DoubleValue = dis.readDouble();
            String StringVaule = dis.readUTF();

            dos.writeLong(LongValue);
            dos.writeDouble(DoubleValue);
            dos.writeUTF(StringVaule);

    }
}
