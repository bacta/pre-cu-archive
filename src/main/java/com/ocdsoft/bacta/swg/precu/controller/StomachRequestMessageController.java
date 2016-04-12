package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.player.StomachRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = StomachRequestMessage.class)
public class StomachRequestMessageController implements GameNetworkMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public StomachRequestMessageController() {

    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {
        logger.info("Requested");
        //StomachResponseMessage stomachResponseMessage = new StomachResponseMessage();
        //This should call some type of method that updates the stomachs latest status.
        //I guess that means they just scheduled a task in the future for when the stomach would be empty.
        //Any checks before then would just calculate how long before the stomach would be empty based on that time and the current time.
    }
}
