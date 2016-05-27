package com.ocdsoft.bacta.swg.server.game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.message.NewbieTutorialResponse;

@MessageHandled(handles = NewbieTutorialResponse.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class NewbieTutorialResponseController implements GameNetworkMessageController<NewbieTutorialResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewbieTutorialResponseController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, NewbieTutorialResponse message) {
        LOGGER.warn("This controller is not implemented");
    }
}

