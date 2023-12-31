package com.bin.push.common.server;


import com.bin.push.common.protocol.MessageDecoder;
import com.bin.push.common.protocol.MessageEncoder;
import com.bin.push.common.resolver.IdleHandler;
import com.bin.push.common.resolver.MessageService;
import com.bin.push.common.resolver.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;


public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {
    private MessageService messageService;

    public NettyChannelInitializer(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(2 * 1024, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new IdleStateHandler(30, 0, 0));
        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new ServerHandler(messageService));
        pipeline.addLast(new IdleHandler());
    }
}
