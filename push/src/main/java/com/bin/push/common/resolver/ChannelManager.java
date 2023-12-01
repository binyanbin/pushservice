package com.bin.push.common.resolver;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.util.Map;

public class ChannelManager {
    private static final Map<String, Channel> MAP_CHANNEL = Maps.newConcurrentMap();
    private static final Map<String, String> MAP_SESSION = Maps.newConcurrentMap();

    public static void register(String sessionId, Channel channel) {
        if (MAP_CHANNEL.containsKey(sessionId)) {
            Channel channel1 = MAP_CHANNEL.get(sessionId);
            if (!channel1.id().asLongText().equals(channel.id().asLongText())) {
                channel1.close();
                MAP_SESSION.remove(channel1.id().asLongText());
                newChannel(sessionId, channel);
            }
        } else {
            newChannel(sessionId, channel);
        }
    }

    public static Channel getChannel(String sessionId) {
        return MAP_CHANNEL.get(sessionId);
    }

    public static boolean contain(Channel channel) {
        return MAP_SESSION.containsKey(channel.id().asLongText());
    }

    public static void close(Channel channel) {
        channel.close();
        String channelId = channel.id().asLongText();
        if (MAP_SESSION.containsKey(channel.id().asLongText())) {
            String sessionId = MAP_SESSION.get(channelId);
            MAP_CHANNEL.remove(sessionId);
        }
        MAP_SESSION.remove(channelId);
    }

    private static void newChannel(String sessionId, Channel channel) {
        MAP_CHANNEL.put(sessionId, channel);
        MAP_SESSION.put(channel.id().asLongText(), sessionId);
    }
}
