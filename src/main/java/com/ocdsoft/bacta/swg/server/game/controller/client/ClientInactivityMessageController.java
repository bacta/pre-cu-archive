package com.ocdsoft.bacta.swg.server.game.controller.client;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.client.ClientInactivityMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ClientInactivityMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientInactivityMessageController implements GameNetworkMessageController<ClientInactivityMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientInactivityMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientInactivityMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

