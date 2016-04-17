package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.player.GuildRequestMessage;
import com.ocdsoft.bacta.swg.precu.message.player.GuildResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = GuildRequestMessage.class)
public class GuildRequestMessageController implements GameNetworkMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public GuildRequestMessageController() {

    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {

        long targetId = message.readLong();

        //TODO: Lookup player.

        GuildResponseMessage response = new GuildResponseMessage(targetId);
        client.sendMessage(response);

    }
}
