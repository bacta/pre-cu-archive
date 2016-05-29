package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import lombok.Getter;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by crush on 5/27/2016.
 */
@Getter
@Singleton
public class ChatServerConfiguration {
    private static final String SECTION = "Bacta/ChatServer";

    private final BactaConfiguration configuration;

    @Inject
    public ChatServerConfiguration(final BactaConfiguration configuration) {
        this.configuration = configuration;
    }

    public InetSocketAddress getBindAddress() throws UnknownHostException {
        final String bindAddress = configuration.getStringWithDefault(SECTION, "BindAddress", "localhost");
        return new InetSocketAddress(
                "localhost".equalsIgnoreCase(bindAddress) ? InetAddress.getLocalHost() : InetAddress.getByName(bindAddress),
                configuration.getIntWithDefault(SECTION, "UdpPort", 44491));
    }

    public Set<String> getRoomsToCreate() {
        return new HashSet<>(configuration.getStringCollection(SECTION, "CreateRoom"));
    }

    public ChatAvatarId getSystemAvatarId() {
        return new ChatAvatarId(
                configuration.getStringWithDefault(SECTION, "SystemAvatar.Game", "swg").toLowerCase(),
                configuration.getStringWithDefault(SECTION, "SystemAvatar.Cluster", "chat").toLowerCase(),
                configuration.getStringWithDefault(SECTION, "SystemAvatar.Name", "system").toLowerCase());
    }

    public InetSocketAddress getGameServerAddress() throws UnknownHostException {
        final String bindAddress = configuration.getStringWithDefault(SECTION, "GameServer.BindAddress", "localhost");
        return new InetSocketAddress(
                "localhost".equalsIgnoreCase(bindAddress) ? InetAddress.getLocalHost() : InetAddress.getByName(bindAddress),
                configuration.getIntWithDefault(SECTION, "GameServer.UdpPort", 44463));
    }


}
