package com.bin.push.common.db;


import com.bin.push.mybatis.base.dao.MessageMapper;
import com.bin.push.mybatis.base.dao.SessionMapper;
import com.bin.push.mybatis.base.model.Message;
import com.bin.push.mybatis.base.model.MessageExample;
import com.bin.push.mybatis.base.model.Session;
import com.bin.push.mybatis.base.model.SessionExample;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


@Service
public class Repository {

    private SessionMapper sessionMapper;

    private MessageMapper messageMapper;

    public Repository(ApplicationContext applicationContext) {
        this.sessionMapper = applicationContext.getBean(SessionMapper.class);
        this.messageMapper = applicationContext.getBean(MessageMapper.class);
    }

    public Session getSession(String sessionId) {
        SessionExample example = new SessionExample();
        example.createCriteria().andSessionIdEqualTo(sessionId);
        List<Session> sessions = sessionMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(sessions)) {
            return null;
        } else {
            return sessions.get(0);
        }
    }

    public List<Message> listMessageBySessionId(String userId, Date begin, Date end) {
        MessageExample example = new MessageExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andCreatedTimeBetween(begin, end).andSendTimeIsNull();
        return messageMapper.selectByExample(example);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(List<Message> messages, Date now) {
        for (Message message : messages) {
            message.setSendTime(now);
            messageMapper.updateByPrimaryKey(message);
        }
    }


}
