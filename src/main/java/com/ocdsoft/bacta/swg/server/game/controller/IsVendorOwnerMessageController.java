package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.IsVendorOwnerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = IsVendorOwnerMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class IsVendorOwnerMessageController implements GameNetworkMessageController<IsVendorOwnerMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IsVendorOwnerMessageController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, IsVendorOwnerMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

