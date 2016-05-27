package com.ocdsoft.bacta.swg.server.game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.message.RequestCategoriesMessage;

@MessageHandled(handles = RequestCategoriesMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class RequestCategoriesMessageController implements GameNetworkMessageController<RequestCategoriesMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCategoriesMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, RequestCategoriesMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

