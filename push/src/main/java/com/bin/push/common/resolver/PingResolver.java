package com.bin.push.common.resolver;

import com.bin.push.common.db.Repository;
import com.bin.push.common.protocol.MessageFactory;
import com.bin.push.common.protocol.MessageType;
import com.bin.push.common.protocol.ReceiveMessage;
import com.bin.push.common.protocol.SendMessage;
import com.bin.push.mybatis.base.model.Message;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;


public class PingResolver implements IResolver {

    private Repository repository;

    public PingResolver(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean support(ReceiveMessage receiveMessage) {
        return receiveMessage.getMessageType() == MessageType.PING;
    }

    @Override
    public SendMessage resolve(ReceiveMessage receiveMessage) {
        if (repository.exitsSessionId(receiveMessage.getSessionId())) {
            Date now = new Date();
            Date begin = DateUtils.addMinutes(now, -30);
            List<Message> messages = repository.listMessageBySessionId(receiveMessage.getSessionId(), begin, now);
            if (messages.size() == 0) {
                return MessageFactory.createPong();
            } else {
                List<String> contents = Lists.newArrayList();
                List<Message> messageList = Lists.newArrayList();
                for (Message message : messages) {
                    contents.add(message.getContent());
                    messageList.add(message);
                }
                repository.sendMessage(messageList, now);
                return MessageFactory.createInfo(contents);
            }
        } else {
            return MessageFactory.createCloseMsg();
        }
    }
}
