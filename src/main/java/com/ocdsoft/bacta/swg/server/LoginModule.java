package com.ocdsoft.bacta.swg.server;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.engine.serialize.NetworkSerializer;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.SubscriptionService;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.game.data.LoginObjectSerializer;
import com.ocdsoft.bacta.swg.server.login.LoginNetworkConfiguration;
import com.ocdsoft.bacta.swg.server.login.LoginServer;
import com.ocdsoft.bacta.swg.server.login.LoginServerState;
import com.ocdsoft.bacta.swg.server.login.service.LoginSubscriptionService;

public class LoginModule extends AbstractModule implements Module {

	@Override
	protected void configure() {

		bind(ServerState.class).to(LoginServerState.class);
		bind(NetworkConfiguration.class).to(LoginNetworkConfiguration.class);
		bind(OutgoingConnectionService.class).to(LoginServer.LoginOutgoingConnectionService.class);
        bind(NetworkSerializer.class).to(LoginObjectSerializer.class);
		bind(SubscriptionService.class).to(LoginSubscriptionService.class);

	}

}
