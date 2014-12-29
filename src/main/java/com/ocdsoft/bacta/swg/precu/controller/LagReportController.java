package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.zone.LagReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = LagReport.class)
public class LagReportController implements SwgMessageController<GameClient> {

    private static Logger logger = LoggerFactory.getLogger("LagReportController");

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        int value1 = message.readInt();
        int value2 = message.readInt();

        logger.debug("Lag Report: " + value1 + " " + value2);
    }
}
