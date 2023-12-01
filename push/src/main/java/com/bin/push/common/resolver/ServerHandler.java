package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageFactory;
import com.bin.push.common.protocol.MessageType;
import com.bin.push.common.protocol.ReceiveMessage;
import com.bin.push.common.protocol.SendMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<ReceiveMessage> {


    private final MessageService messageService;

    public ServerHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveMessage receiveMessage) {
        if (receiveMessage.getMessageType().equals(MessageType.REGISTER)) {
            String sessionId = receiveMessage.getBody();
            if (sessionId.length() == 32) {
                ChannelManager.register(sessionId, ctx.channel());
                SendMessage msg = messageService.resolve(sessionId);
                if (msg.getType() == MessageType.CLOSE) {
                    ChannelManager.close(ctx.channel());
                } else {
                    ctx.writeAndFlush(msg);
                }
            }
        } else if (receiveMessage.getMessageType().equals(MessageType.TRANSFORM)) {
            String sessionId = receiveMessage.getBody();
            if (sessionId.length() == 32) {
                Channel channel = ChannelManager.getChannel(sessionId);
                if (channel != null) {
                    SendMessage msg = messageService.resolve(sessionId);
                    if (msg.getType() == MessageType.CLOSE) {
                        ChannelManager.close(channel);
                    } else {
                        channel.writeAndFlush(msg);
                    }
                } else {
                    ctx.writeAndFlush(MessageFactory.createPong());
                }
            } else {
                ctx.writeAndFlush(MessageFactory.createPong());
            }
        } else {
            if (receiveMessage.getMessageType().equals(MessageType.PING)) {
                if (!ChannelManager.contain(ctx.channel())) {
                    ChannelManager.close(ctx.channel());
                    return;
                }
                ctx.writeAndFlush(MessageFactory.createPong());
            } else {
                ChannelManager.close(ctx.channel());
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ChannelManager.close(ctx.channel());
    }

}
