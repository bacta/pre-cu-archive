package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.zone.LagReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = LagReport.class)
public class LagReportController implements GameNetworkMessageController<GameClient> {

    private static Logger logger = LoggerFactory.getLogger("LagReportController");

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {

        int value1 = message.readInt();
        int value2 = message.readInt();

        logger.debug("Lag Report: " + value1 + " " + value2);
    }
}
