package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageType;
import com.bin.push.common.protocol.ReceiveMessage;
import com.bin.push.common.protocol.SendMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<ReceiveMessage> {

    private final ResolverFactory resolverFactory;

    public ServerHandler(ResolverFactory resolverFactory) {
        this.resolverFactory = resolverFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveMessage receiveMessage) {
        System.out.println("biz says: " + receiveMessage.getSessionId());
        IResolver IResolver = resolverFactory.getMessageResolver(receiveMessage);
        SendMessage msg = IResolver.resolve(receiveMessage);
        if (msg.getType() == MessageType.CLOSE) {
            ctx.channel().close();
        } else {
            ctx.writeAndFlush(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
