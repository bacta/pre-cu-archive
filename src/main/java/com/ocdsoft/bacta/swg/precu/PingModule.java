package com.ocdsoft.bacta.swg.precu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.soe.io.udp.game.PingObjectSerializer;
import com.ocdsoft.bacta.soe.io.udp.game.PingServerState;
import com.ocdsoft.bacta.swg.network.soe.ServerState;
import com.ocdsoft.conf.BactaConfiguration;
import com.ocdsoft.conf.ini.IniBactaConfiguration;
import com.ocdsoft.network.data.serialization.NetworkSerializer;

public class PingModule extends AbstractModule implements Module {

	@Override
	protected void configure() {
        bind(NetworkSerializer.class).to(PingObjectSerializer.class);
        bind(ServerState.class).to(PingServerState.class);

        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);

	}

}
