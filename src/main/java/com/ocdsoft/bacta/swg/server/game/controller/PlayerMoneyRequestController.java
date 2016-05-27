package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.PlayerMoneyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = PlayerMoneyRequest.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class PlayerMoneyRequestController implements GameNetworkMessageController<PlayerMoneyRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMoneyRequestController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, PlayerMoneyRequest message) {
        LOGGER.warn("This controller is not implemented");
    }
}

