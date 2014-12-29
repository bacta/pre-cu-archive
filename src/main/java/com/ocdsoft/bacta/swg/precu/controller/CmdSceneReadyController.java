package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatSystemMessage;
import com.ocdsoft.bacta.swg.server.game.message.zone.CmdSceneReady;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = CmdSceneReady.class)
public class CmdSceneReadyController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        client.sendMessage(new CmdSceneReady());

        ChatSystemMessage motd = new ChatSystemMessage("Welcome to Bacta!");
        client.sendMessage(motd);

        logger.info("Scene Ready for " + client.getConnectionId());
    }
}
