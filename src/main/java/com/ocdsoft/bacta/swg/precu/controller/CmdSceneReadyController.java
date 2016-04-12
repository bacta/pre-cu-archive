package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatSystemMessage;
import com.ocdsoft.bacta.swg.precu.message.zone.CmdSceneReady;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = CmdSceneReady.class)
public class CmdSceneReadyController implements GameNetworkMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {

        client.sendMessage(new CmdSceneReady());

        ChatSystemMessage motd = new ChatSystemMessage("Welcome to Bacta!");
        client.sendMessage(motd);

        logger.info("Scene Ready for " + client.getConnectionId());
    }
}
