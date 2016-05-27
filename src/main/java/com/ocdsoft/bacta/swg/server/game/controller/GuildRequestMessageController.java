package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.GuildRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = GuildRequestMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class GuildRequestMessageController implements GameNetworkMessageController<GuildRequestMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuildRequestMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, GuildRequestMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

