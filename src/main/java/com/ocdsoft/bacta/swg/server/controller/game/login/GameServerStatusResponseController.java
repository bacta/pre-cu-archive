package com.ocdsoft.bacta.swg.server.controller.game.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.message.TerminateReason;
import com.ocdsoft.bacta.swg.server.message.login.GameServerStatusResponse;
import com.ocdsoft.bacta.swg.server.object.login.ClusterEntry;


/**
 * Created by kburkhardt on 1/31/15.
 */
@MessageHandled(handles = GameServerStatusResponse.class)
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
public class GameServerStatusResponseController implements GameNetworkMessageController<GameServerStatusResponse> {
    
    private final GameServerState<ClusterEntry> serverState;
    
    @Inject
    public GameServerStatusResponseController(final GameServerState<ClusterEntry> serverState) {
        this.serverState = serverState;
    }
    
    
    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, GameServerStatusResponse message) throws Exception {
        loginConnection.terminate(TerminateReason.NONE);
    }
}
