package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class IdleHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger lossConnectCount = new AtomicInteger(0);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                lossConnectCount.getAndIncrement();
                if (lossConnectCount.get() > 1) {
                    if (lossConnectCount.get() > 2) {
                        ctx.channel().close();
                    } else {
                        ctx.writeAndFlush(MessageFactory.createCloseMsg());
                        super.userEventTriggered(ctx, evt);
                    }
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("client says: " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
