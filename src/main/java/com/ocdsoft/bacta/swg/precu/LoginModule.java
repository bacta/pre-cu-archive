package com.ocdsoft.bacta.swg.precu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.login.LoginNetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServerState;

public class LoginModule extends AbstractModule implements Module {

	@Override
	protected void configure() {
        bind(ServerState.class).to(LoginServerState.class);
		bind(NetworkConfiguration.class).to(LoginNetworkConfiguration.class);
	}

}
