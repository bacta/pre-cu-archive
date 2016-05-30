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

import java.util.ResourceBundle;

/**
 * Created by kyle on 4/11/2016.
 */
@Singleton
@Getter
public final class PreCuGameServerState implements GameServerState {
    private final int clusterId;
    private final ServerType serverType;
    private ServerStatus serverStatus;
    private final ClusterServer clusterServer;
    private final String branch;
    private final int version;
    private final String networkVersion;

    @Inject
    public PreCuGameServerState(final BactaConfiguration configuration,
                                final GameNetworkConfiguration networkConfiguration) {

        this.serverStatus = ServerStatus.LOADING;
        this.serverType = ServerType.GAME;
        this.clusterId = networkConfiguration.getClusterId();

        final ResourceBundle swgBundle = ResourceBundle.getBundle("git-swg");
        final ResourceBundle soeBundle = ResourceBundle.getBundle("git-soe");
        branch = swgBundle.getString("git.commit.id");
        version = Integer.parseInt(swgBundle.getString("git.commit.id.abbrev"), 16);
        networkVersion = swgBundle.getString("git.commit.id");

        clusterServer = new ClusterServer(configuration, networkConfiguration, this);
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
