package com.ocdsoft.bacta.swg.precu.controller.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.game.LagReport;

@MessageHandled(handles = LagReport.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class LagReportController implements GameNetworkMessageController<LagReport> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LagReportController.class);

    @Override
    public void handleIncoming(SoeUdpConnection connection, LagReport message) {
        LOGGER.debug("Lag Report: " + message.getValue1() + " " + message.getValue2());
    }
}

