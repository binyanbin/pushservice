package com.bin.push.common.protocol;


public class MessageP {
    private MessageType type;
    private String body;


    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
