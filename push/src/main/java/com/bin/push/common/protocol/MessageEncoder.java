package com.bin.push.common.protocol;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class MessageEncoder extends MessageToByteEncoder<SendMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SendMessage message, ByteBuf out) {
        out.writeByte(message.getType().getType());
        if (StringUtils.hasText(message.getBody())) {
            out.writeShort(message.getBody().getBytes(StandardCharsets.UTF_8).length);
            out.writeCharSequence(message.getBody(), Charset.defaultCharset());
        } else {
            out.writeShort(0);
        }
    }
}