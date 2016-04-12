package com.ocdsoft.bacta.swg.precu;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.ClusterEntryItem;
import lombok.Data;

/**
 * Created by kyle on 4/11/2016.
 */
@Singleton
@Data
public final class PreCuGameServerState implements GameServerState {
    private int id;
    private ServerType serverType;
    private ServerStatus serverStatus;
}
