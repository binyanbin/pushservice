package com.bin.push.common.resolver;

import com.bin.push.common.db.Repository;
import com.bin.push.common.protocol.MessageFactory;
import com.bin.push.common.protocol.MessageType;
import com.bin.push.common.protocol.ReceiveMessaage;
import com.bin.push.common.protocol.SendMessage;
import com.bin.push.mybatis.base.model.Message;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class PingResolver implements IResolver {

    private Repository repository;

    public PingResolver(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean support(ReceiveMessaage receiveMessaage) {
        return receiveMessaage.getMessageType() == MessageType.PING;
    }

    @Override
    public SendMessage resolve(ReceiveMessaage receiveMessaage) throws ParseException {
//        if (receiveMessaage.getSessionId().equals("9822dcdb014a4699ae878523fb781d7e")) {
//            return MessageFactory.createInfo("这是一个测试消息");
//        } else {
//            return MessageFactory.createPong();
//        }

        if (repository.exitsSessionId(receiveMessaage.getSessionId())) {
            Date now = new Date();
            Date begin = DateUtils.addMinutes(now, -30);
            List<Message> messages = repository.listMessageBySessionId(receiveMessaage.getSessionId(), begin, now);
            if (messages.size() == 0) {
                return MessageFactory.createPong();
            } else {
                List<String> contents = Lists.newArrayList();
                List<Long> ids = Lists.newArrayList();
                for (Message message : messages) {
                    contents.add(message.getContent());
                    ids.add(message.getId());
                }
                repository.sendMessage(ids, now);
                return MessageFactory.createInfo(contents);
            }
        } else {
            return MessageFactory.createCloseMsg();
        }
    }
}
