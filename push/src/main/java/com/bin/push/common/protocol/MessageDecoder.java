package com.bin.push.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        CharSequence sessionId = byteBuf.readCharSequence(
                32, Charset.defaultCharset());
        receiveMessage.setSessionId((String) sessionId);
        receiveMessage.setMessageType(MessageType.get(byteBuf.readByte()));
        int bodyLength = byteBuf.readShort();
        if (bodyLength > 0) {
            CharSequence body = byteBuf.readCharSequence(bodyLength, Charset.defaultCharset());
            receiveMessage.setBody(body.toString());
        }
        out.add(receiveMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.writeAndFlush(MessageFactory.createCloseMsg());
    }
}