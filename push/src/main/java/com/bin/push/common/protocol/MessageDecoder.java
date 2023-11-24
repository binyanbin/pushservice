package com.bin.push.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        ReceiveMessaage receiveMessaage = new ReceiveMessaage();
        CharSequence sessionId = byteBuf.readCharSequence(
                32, Charset.defaultCharset());
        receiveMessaage.setSessionId((String) sessionId);
        receiveMessaage.setMessageType(MessageType.get(byteBuf.readByte()));
        int bodyLength = byteBuf.readShort();
        if (bodyLength > 0) {
            CharSequence body = byteBuf.readCharSequence(bodyLength, Charset.defaultCharset());
            receiveMessaage.setBody(body.toString());
        }
        out.add(receiveMessaage);
    }
}