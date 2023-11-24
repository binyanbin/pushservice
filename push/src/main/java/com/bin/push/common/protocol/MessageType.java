package com.bin.push.common.protocol;

public enum MessageType {
    PING((byte) 1), PONG((byte) 2), INFO((byte) 3), CLOSE((byte) 4), ;

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
        throw new RuntimeException("unsupported type: " + type);
    }

    public int getType() {
        return type;
    }
}