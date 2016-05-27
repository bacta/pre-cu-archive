package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.FactionRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = FactionRequestMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class FactionRequestMessageController implements GameNetworkMessageController<FactionRequestMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactionRequestMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, FactionRequestMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

