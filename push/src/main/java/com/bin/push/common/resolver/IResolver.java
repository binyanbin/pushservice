package com.bin.push.common.resolver;

import com.bin.push.common.protocol.ReceiveMessaage;
import com.bin.push.common.protocol.SendMessage;

public interface IResolver {
    boolean support(ReceiveMessaage receiveMessaage);

    SendMessage resolve(ReceiveMessaage receiveMessaage) throws Exception;
}
