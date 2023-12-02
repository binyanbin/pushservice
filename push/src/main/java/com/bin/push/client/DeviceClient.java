package com.bin.push.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户端
 */
public class DeviceClient extends Thread {
    private String ip;
    private Integer port;
    private String sessionId;
    private final IPushCallback callback;

    public DeviceClient(String ip, Integer port, String sessionId, IPushCallback callback) {
        super();
        this.ip = ip;
        this.port = port;
        this.sessionId = sessionId;
        this.callback = callback;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(35);
            dataOutputStream.writeByte(5);
            dataOutputStream.writeShort(32);
            dataOutputStream.writeBytes(sessionId);
            dataOutputStream.flush();
            while (true) {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                inputStream.readInt();
                inputStream.readByte();
                short s = inputStream.readShort();
                if (s > 0) {
                    byte[] bytes = new byte[s];
                    inputStream.readFully(bytes);
                    String str = new String(bytes, "utf-8");
                    callback.callback(str);
                }
                dataOutputStream.writeInt(3);
                dataOutputStream.writeByte(1);
                dataOutputStream.writeShort(0);
                dataOutputStream.flush();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        DeviceClient client = new DeviceClient("127.0.0.1", 3333, "bc94a6b80f0345d9a2a221668890f895",
                str -> System.out.println(str));
        client.run();
    }

}
