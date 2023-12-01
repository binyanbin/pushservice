package com.bin.push.common.resolver;

import com.bin.push.common.protocol.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdleHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger lossConnectCount = new AtomicInteger(0);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectCount.getAndIncrement();
                if (lossConnectCount.get() > 1) {
                    if (lossConnectCount.get() > 2) {
                        String sessionId = ChannelManager.getSessionId(ctx.channel());
                        if (StringUtils.hasText(sessionId)) {
                            System.out.println("[" + sessionId + "]closed");
                        }
                        ChannelManager.close(ctx.channel());
                    } else {
                        String sessionId = ChannelManager.getSessionId(ctx.channel());
                        if (StringUtils.hasText(sessionId)) {
                            System.out.println("[" + sessionId + "] will be closed");
                        }
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ChannelManager.close(ctx.channel());
    }
}
