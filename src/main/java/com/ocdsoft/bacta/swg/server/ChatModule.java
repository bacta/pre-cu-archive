package com.ocdsoft.bacta.swg.server;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.engine.serialize.NetworkSerializer;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatServer;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatServerState;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.server.data.ChatObjectSerializer;

public class ChatModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(ServerState.class).to(ChatServerState.class);
        bind(NetworkConfiguration.class).to(ChatNetworkConfiguration.class);
        bind(OutgoingConnectionService.class).to(ChatServer.ChatOutgoingConnectionService.class);
        bind(NetworkSerializer.class).to(ChatObjectSerializer.class);

        bind(SwgChatServer.class).asEagerSingleton();
    }

}
