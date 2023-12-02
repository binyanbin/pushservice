package com.bin.push.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 业务系统
 */
public class BizClient {

    private String ip;
    private Integer port;
    private Socket socket;

    public BizClient(String ip, Integer port) throws IOException {
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
    }

    //触发推送
    public void active(String sessionId) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(35);
        dataOutputStream.writeByte(6);
        dataOutputStream.writeShort(32);
        dataOutputStream.writeBytes(sessionId);
        dataOutputStream.flush();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        BizClient bizClient = new BizClient("127.0.0.1", 3333);
        bizClient.active("bc94a6b80f0345d9a2a221668890f895");
    }

}
