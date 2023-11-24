package com.bin.push.common.protocol;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class MessageFactory {

    public static SendMessage createPong() {
        SendMessage message = new SendMessage();
        message.setType(MessageType.PONG);
        message.setBody("");
        return message;
    }

    public static SendMessage createCloseMsg() {
        SendMessage info = new SendMessage();
        info.setType(MessageType.CLOSE);
        return info;
    }

    public static SendMessage createInfo(String obj) {
        SendMessage info = new SendMessage();
        info.setBody(obj);
        info.setType(MessageType.INFO);
        return info;
    }

    public static SendMessage createInfo(List<String> content) {
        SendMessage info = new SendMessage();
        info.setBody(JSON.toJSONString(content));
        info.setType(MessageType.INFO);
        return info;
    }
}
