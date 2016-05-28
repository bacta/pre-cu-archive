package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.GcwRegionsReq;
import com.ocdsoft.bacta.swg.server.game.message.GcwRegionsRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/27/2016.
 */
@MessageHandled(handles = GcwRegionsReq.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class GcwRegionsReqController implements GameNetworkMessageController<GcwRegionsReq> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcwRegionsReqController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GcwRegionsReq message) throws Exception {
        LOGGER.warn("Not implemented");
        connection.sendMessage(new GcwRegionsRsp());
    }
}