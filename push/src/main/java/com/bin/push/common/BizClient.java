package com.bin.push.common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 业务系统
 */
public class BizClient {

    public static void push(String ip, int port, String sessionId) throws IOException {
        Socket socket = new Socket(ip, port);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(35);
        dataOutputStream.writeByte(6);
        dataOutputStream.writeShort(32);
        dataOutputStream.writeBytes(sessionId);
        dataOutputStream.flush();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        push("127.0.0.1", 3333, "bc94a6b80f0345d9a2a221668890f895");
    }

}
