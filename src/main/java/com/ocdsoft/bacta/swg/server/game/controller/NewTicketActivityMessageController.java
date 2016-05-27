package com.ocdsoft.bacta.swg.server.game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.message.NewTicketActivityMessage;

@MessageHandled(handles = NewTicketActivityMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class NewTicketActivityMessageController implements GameNetworkMessageController<NewTicketActivityMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewTicketActivityMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, NewTicketActivityMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

