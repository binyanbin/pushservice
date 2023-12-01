package com.bin.push.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * 客户端
 */
public class NormalClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 3333);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(35);
        dataOutputStream.writeByte(5);
        dataOutputStream.writeShort(32);
        dataOutputStream.writeBytes("bc94a6b80f0345d9a2a221668890f895");
        dataOutputStream.flush();

        while(true) {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            inputStream.readInt();
            inputStream.readByte();
            short s = inputStream.readShort();
            if (s>0){
                byte[] bytes = new byte[s];
                inputStream.readFully(bytes);
                String str = new String(bytes,"utf-8");
                System.out.println(str);
            }

            dataOutputStream.writeInt(3);
            dataOutputStream.writeByte(1);
            dataOutputStream.writeShort(0);
            dataOutputStream.flush();
        }

    }

}
