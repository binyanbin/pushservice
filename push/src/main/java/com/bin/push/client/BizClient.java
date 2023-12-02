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
    private String sessionId;

    public BizClient(String ip, Integer port, String sessionId) {
        this.ip = ip;
        this.port = port;
        this.sessionId = sessionId;
    }

    //触发推送
    public void active() throws IOException {
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
        BizClient bizClient = new BizClient("127.0.0.1", 3333, "bc94a6b80f0345d9a2a221668890f895");
        bizClient.active();
    }

}
