package com.ocdsoft.bacta.swg.precu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.game.GameNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;

public class GameModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(ServerState.class).to(GameServerState.class);
        bind(NetworkConfiguration.class).to(GameNetworkConfiguration.class);
    }

}
