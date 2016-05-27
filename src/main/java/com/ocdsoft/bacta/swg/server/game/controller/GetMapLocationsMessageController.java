package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.GetMapLocationsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = GetMapLocationsMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class GetMapLocationsMessageController implements GameNetworkMessageController<GetMapLocationsMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetMapLocationsMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, GetMapLocationsMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

