package com.ocdsoft.bacta.swg.server;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatServerState;

public class ChatModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(ServerState.class).to(ChatServerState.class);
        bind(NetworkConfiguration.class).to(ChatNetworkConfiguration.class);
    }

}
