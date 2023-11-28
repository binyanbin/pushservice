package com.bin.push.common.resolver;

import com.bin.push.common.protocol.ReceiveMessage;
import com.bin.push.common.db.Repository;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ResolverFactory {

    private final List<IResolver> RESOLVERS = new CopyOnWriteArrayList<>();

    public ResolverFactory(ApplicationContext applicationContext) {
        Repository repository = applicationContext.getBean(Repository.class);
        register(new PingResolver(repository));
    }


    public void register(IResolver resolver) {
        RESOLVERS.add(resolver);
    }

    public IResolver getMessageResolver(ReceiveMessage receiveMessage) {
        for (IResolver IResolver : RESOLVERS) {
            if (IResolver.support(receiveMessage)) {
                return IResolver;
            }
        }
        throw new RuntimeException("cannot find resolver, message type: " + receiveMessage.getMessageType());
    }
}