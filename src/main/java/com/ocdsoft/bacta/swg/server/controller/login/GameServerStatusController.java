package com.ocdsoft.bacta.swg.server.controller.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.object.ClusterEntryItem;
import com.ocdsoft.bacta.soe.service.ClusterService;
import com.ocdsoft.bacta.swg.server.message.login.GameServerStatus;
import com.ocdsoft.bacta.swg.server.message.login.GameServerStatusResponse;
import com.ocdsoft.bacta.swg.server.object.login.ClusterEntry;

/**
 * Created by kburkhardt on 1/31/15.
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = GameServerStatus.class, type = ServerType.LOGIN)
public class GameServerStatusController implements GameNetworkMessageController<GameServerStatus> {

    private final ClusterService<ClusterEntry> clusterService;

    @Inject
    public GameServerStatusController(final ClusterService<ClusterEntry> clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection gameConnection, GameServerStatus message) throws Exception {
        ClusterEntryItem clusterEntryItem = clusterService.updateClusterInfo(message.getClusterEntry());
        
        GameServerStatusResponse gameServerStatusResponse = new GameServerStatusResponse(clusterEntryItem.getId());
        gameConnection.sendMessage(gameServerStatusResponse);
        //gameConnection.terminate(TerminateReason.NONE);
    }
}
