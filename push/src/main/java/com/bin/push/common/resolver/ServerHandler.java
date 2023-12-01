package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageFactory;
import com.bin.push.common.protocol.MessageP;
import com.bin.push.common.protocol.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<MessageP> {


    private final MessageService messageService;

    public ServerHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageP messageP) {
        if (messageP.getType().equals(MessageType.REGISTER)) {
            String sessionId = messageP.getBody();
            if (sessionId.length() == 32) {
                ChannelManager.register(sessionId, ctx.channel());
                MessageP msg = messageService.resolve(sessionId);
                if (msg.getType() == MessageType.CLOSE) {
                    ChannelManager.close(ctx.channel());
                } else {
                    ctx.writeAndFlush(msg);
                }
            }
        } else if (messageP.getType().equals(MessageType.TRANSFORM)) {
            String sessionId = messageP.getBody();
            if (sessionId.length() == 32) {
                Channel channel = ChannelManager.getChannel(sessionId);
                if (channel != null) {
                    MessageP msg = messageService.resolve(sessionId);
                    if (msg.getType() == MessageType.CLOSE) {
                        ChannelManager.close(channel);
                    } else if (msg.getType().equals(MessageType.INFO)) {
                        channel.writeAndFlush(msg);
                    }
                }
            }
            ctx.writeAndFlush(MessageFactory.createPong());
        } else {
            if (messageP.getType().equals(MessageType.PING)) {
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
