package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.zone.ConnectionServerLagResponse;
import com.ocdsoft.bacta.swg.server.game.message.zone.GameServerLagResponse;
import com.ocdsoft.bacta.swg.server.game.message.zone.LagRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = LagRequest.class)
public class LagRequestController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {
        client.sendMessage(new ConnectionServerLagResponse());
        client.sendMessage(new GameServerLagResponse());
    }
}
