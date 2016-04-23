package com.ocdsoft.bacta.swg.precu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.ClusterEntryItem;
import com.ocdsoft.bacta.swg.precu.object.login.ClusterEntry;
import lombok.Data;

/**
 * Created by kyle on 4/11/2016.
 */
@Singleton
@Data
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
}
