package com.bin.push.common.protocol;

public enum MessageType {
    PING((byte) 1),//客户端心跳消息
    PONG((byte) 2),//服务端响应,无信息
    INFO((byte) 3),//服务端响应,有信息
    CLOSE((byte) 4),//服务端关闭链接消息
    REGISTER((byte) 5),//客户端注册消息
    TRANSFORM((byte) 6),//业务系统推送请求消息
    UNKNOWN((byte) 99),//未知消息
    ;
    private byte type;

    MessageType(byte type) {
        this.type = type;
    }

    public static MessageType get(byte type) {
        for (MessageType value : values()) {
            if (value.type == type) {
                return value;
            }
        }
        return MessageType.UNKNOWN;
    }

    public int getType() {
        return type;
    }
}