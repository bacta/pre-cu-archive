package com.ocdsoft.bacta.swg.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.GameNetworkConfiguration;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.login.object.ClusterServer;
import lombok.Getter;

/**
 * Created by kyle on 4/11/2016.
 */
@Singleton
@Getter
public final class PreCuGameServerState implements GameServerState {
    private int id;
    private ServerType serverType;
    private ServerStatus serverStatus;
    private ClusterServer clusterServer;

    @Inject
    public PreCuGameServerState(final BactaConfiguration configuration, final GameNetworkConfiguration networkConfiguration) {
        this.serverStatus = ServerStatus.LOADING;
        this.serverType = ServerType.GAME;
        clusterServer = new ClusterServer(configuration, networkConfiguration);
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
        clusterServer.getStatusClusterData().setStatus(serverStatus);
    }

    @Override
    public void setOnlineUsers(int onlineUsers) {
        clusterServer.getStatusClusterData().setPopulationOnline(onlineUsers);
    }
}
