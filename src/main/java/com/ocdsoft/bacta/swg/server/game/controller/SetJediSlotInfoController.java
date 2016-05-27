package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.SetJediSlotInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = SetJediSlotInfo.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class SetJediSlotInfoController implements GameNetworkMessageController<SetJediSlotInfo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetJediSlotInfoController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final SetJediSlotInfo message) throws Exception {
        LOGGER.warn("Not implemented");
    }
}