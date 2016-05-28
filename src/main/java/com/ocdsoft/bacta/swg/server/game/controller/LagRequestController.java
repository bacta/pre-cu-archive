package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.ConnectionServerLagResponse;
import com.ocdsoft.bacta.swg.server.game.message.GameServerLagResponse;
import com.ocdsoft.bacta.swg.server.game.message.LagRequest;

@MessageHandled(handles = LagRequest.class)
@ConnectionRolesAllowed({})
public class LagRequestController implements GameNetworkMessageController<LagRequest> {

    @Override
    public void handleIncoming(SoeUdpConnection connection, LagRequest message) {
        connection.sendMessage(new ConnectionServerLagResponse());
        connection.sendMessage(new GameServerLagResponse());
    }
}

