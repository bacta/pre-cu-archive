package com.ocdsoft.bacta.swg.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.swg.server.object.login.ClusterEntry;
import lombok.Getter;

/**
 * Created by kyle on 4/11/2016.
 */
@Singleton
@Getter
public final class PreCuGameServerState implements GameServerState<ClusterEntry> {
    private int id;
    private ServerType serverType;
    private ServerStatus serverStatus;
    private ClusterEntry clusterEntry;

    @Inject
    public PreCuGameServerState(final BactaConfiguration configuration) {
        this.serverStatus = ServerStatus.LOADING;
        this.serverType = ServerType.GAME;
        clusterEntry = new ClusterEntry(configuration);
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
        clusterEntry.getStatusClusterData().setStatus(serverStatus);
    }

    @Override
    public void setOnlineUsers(int onlineUsers) {
        clusterEntry.getStatusClusterData().setPopulationOnline(onlineUsers);
    }
}
