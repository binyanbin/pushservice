package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageP;

/**
 * 获取推送信息
 */
public interface IMessageService {
    MessageP getMessage(String sessionId);
}
