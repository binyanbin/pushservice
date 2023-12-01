package com.bin.push.common.protocol;

import com.alibaba.fastjson.JSON;
import com.bin.push.mybatis.base.model.Message;

import java.util.List;

public class MessageFactory {

    public static MessageP createPong() {
        MessageP message = new MessageP();
        message.setType(MessageType.PONG);
        message.setBody("");
        return message;
    }

    public static MessageP createCloseMsg() {
        MessageP info = new MessageP();
        info.setType(MessageType.CLOSE);
        return info;
    }

    public static MessageP createInfo(String obj) {
        MessageP info = new MessageP();
        info.setBody(obj);
        info.setType(MessageType.INFO);
        return info;
    }

    public static MessageP createInfo(List<String> content) {
        MessageP info = new MessageP();
        info.setBody(JSON.toJSONString(content));
        info.setType(MessageType.INFO);
        return info;
    }
}
