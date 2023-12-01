package com.bin.push;


import com.bin.push.common.db.Repository;
import com.bin.push.common.resolver.MessageService;
import com.bin.push.common.server.NettyChannelInitializer;
import com.bin.push.common.server.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.bin")
@MapperScan(basePackages = "com.bin.push.mybatis.base.dao")
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        NettyServer nettyServer = new NettyServer(new NettyChannelInitializer(new MessageService(new Repository(applicationContext))));
        String port = applicationContext.getEnvironment().getProperty("netty.tcp.port");
        nettyServer.start(Integer.valueOf(port));
    }


}
