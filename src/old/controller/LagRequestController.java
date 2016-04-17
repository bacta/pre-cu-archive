package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.zone.ConnectionServerLagResponse;
import com.ocdsoft.bacta.swg.precu.message.zone.GameServerLagResponse;
import com.ocdsoft.bacta.swg.precu.message.zone.LagRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = LagRequest.class)
public class LagRequestController implements GameNetworkMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {
        client.sendMessage(new ConnectionServerLagResponse());
        client.sendMessage(new GameServerLagResponse());
    }
}
