package com.bin.push.common.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;


public class NettyServer {

    private final NettyChannelInitializer nettyChannelInitializer;

    private Channel serverChannel;

    public NettyServer(NettyChannelInitializer nettyChannelInitializer) {
        this.nettyChannelInitializer = nettyChannelInitializer;
    }

    public void start(int port) throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(nettyChannelInitializer);
        serverChannel = serverBootstrap.bind(new InetSocketAddress(port)).sync().channel().closeFuture().sync().channel();
    }

    public void stop() {
        serverChannel.close();
        serverChannel.parent().close();
    }

}