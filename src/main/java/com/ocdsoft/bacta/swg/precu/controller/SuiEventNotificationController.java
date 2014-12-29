package com.ocdsoft.bacta.swg.precu.controller;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.SuiEventNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = SuiEventNotification.class)
public class SuiEventNotificationController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        int pageId = message.readInt();
        int eventId = message.readInt();

        logger.debug("PageId <{}> and EventId <{}>.", pageId, eventId);

        int size = message.readInt();
        int counter = message.readInt();

        for (int i =0 ; i < size; i++) {
            String val = message.readUnicode();
            logger.debug("Property with value '{}'.", val);
        }

    }
}
