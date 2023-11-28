package com.bin.push.common.resolver;

import com.bin.push.common.protocol.ReceiveMessage;
import com.bin.push.common.protocol.SendMessage;

public interface IResolver {
    boolean support(ReceiveMessage receiveMessage);

    SendMessage resolve(ReceiveMessage receiveMessage);
}
