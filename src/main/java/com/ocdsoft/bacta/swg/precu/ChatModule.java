package com.ocdsoft.bacta.swg.precu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.chat.ChatServerState;
import com.ocdsoft.bacta.soe.io.udp.game.GameNetworkConfiguration;
import org.jivesoftware.smack.Chat;

public class ChatModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(ServerState.class).to(ChatServerState.class);
        bind(NetworkConfiguration.class).to(ChatNetworkConfiguration.class);
    }

}
