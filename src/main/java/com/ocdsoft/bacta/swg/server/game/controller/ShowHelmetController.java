package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.ShowHelmet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ShowHelmet.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ShowHelmetController implements GameNetworkMessageController<ShowHelmet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowHelmetController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, ShowHelmet message) {
        LOGGER.warn("This controller is not implemented");
    }
}

