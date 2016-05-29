package com.ocdsoft.bacta.swg.server;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.engine.serialize.NetworkSerializer;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.MessageSubscriptionService;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.chat.ChatNetworkConfiguration;
import com.ocdsoft.bacta.swg.server.chat.ChatServer;
import com.ocdsoft.bacta.swg.server.chat.ChatServerState;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.server.chat.service.ChatMessageSubscriptionService;
import com.ocdsoft.bacta.swg.server.game.data.ChatObjectSerializer;

public class ChatModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(ServerState.class).to(ChatServerState.class);
        bind(NetworkConfiguration.class).to(ChatNetworkConfiguration.class);
        bind(OutgoingConnectionService.class).to(ChatServer.ChatOutgoingConnectionService.class);
        bind(NetworkSerializer.class).to(ChatObjectSerializer.class);
        bind(MessageSubscriptionService.class).to(ChatMessageSubscriptionService.class);

        bind(SwgChatServer.class).asEagerSingleton();
    }

}
