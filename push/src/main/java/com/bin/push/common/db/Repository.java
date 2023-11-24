package com.bin.push.common.db;


import com.bin.push.mybatis.base.dao.MessageMapper;
import com.bin.push.mybatis.base.dao.SessionMapper;
import com.bin.push.mybatis.base.model.Message;
import com.bin.push.mybatis.base.model.MessageExample;
import com.bin.push.mybatis.base.model.SessionExample;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class Repository {

    private SessionMapper sessionMapper;

    private MessageMapper messageMapper;

    public Repository(SessionMapper sessionMapper, MessageMapper messageMapper) {
        this.sessionMapper = sessionMapper;
        this.messageMapper = messageMapper;
    }

    public boolean exitsSessionId(String sessionId) {
        SessionExample example = new SessionExample();
        example.createCriteria().andSessionIdEqualTo(sessionId);
        return sessionMapper.countByExample(example) > 0;
    }

    public List<Message> listMessageBySessionId(String sessionId, Date begin,Date end) {

        MessageExample example = new MessageExample();
        example.createCriteria().andSessionIdEqualTo(sessionId)
                .andCreatedTimeBetween(begin, end).andSendTimeIsNull();
        return messageMapper.selectByExample(example);
    }

    public void sendMessage(List<Long> ids, Date now) {
        MessageExample example = new MessageExample();
        example.createCriteria().andIdIn(ids);
        Message message = new Message();
        message.setSendTime(now);
        messageMapper.updateByExample(message, example);
    }


}
