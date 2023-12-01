package com.bin.push.common.resolver;

import com.bin.push.common.db.Repository;
import com.bin.push.common.protocol.MessageFactory;
import com.bin.push.common.protocol.SendMessage;
import com.bin.push.mybatis.base.model.Message;
import com.bin.push.mybatis.base.model.Session;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;


public class MessageService {

    private Repository repository;

    public MessageService(Repository repository) {
        this.repository = repository;
    }


    public SendMessage resolve(String sessionId) {
        Session session = repository.getSession(sessionId);
        if (session != null) {
            Date now = new Date();
            Date begin = DateUtils.addMinutes(now, -30);
            List<Message> messages = repository.listMessageBySessionId(session.getUserId().toString(), begin, now);
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
