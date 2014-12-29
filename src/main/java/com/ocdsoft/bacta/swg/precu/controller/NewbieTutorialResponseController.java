package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.tutorial.NewbieTutorialResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = NewbieTutorialResponse.class)
public class NewbieTutorialResponseController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public NewbieTutorialResponseController() {

    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        //TODO: Start the tutorial process if queued.

    }
}
