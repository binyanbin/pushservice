package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageType;
import com.bin.push.common.protocol.ReceiveMessaage;
import com.bin.push.common.protocol.SendMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<ReceiveMessaage> {

    private final ResolverFactory resolverFactory;

    public ServerHandler(ResolverFactory resolverFactory) {
        this.resolverFactory = resolverFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveMessaage receiveMessaage) throws Exception {
        IResolver IResolver = resolverFactory.getMessageResolver(receiveMessaage);
        SendMessage msg = IResolver.resolve(receiveMessaage);
        if (msg.getType() == MessageType.CLOSE) {
            ctx.channel().close();
        } else {
            ctx.writeAndFlush(msg);
        }
    }

}
