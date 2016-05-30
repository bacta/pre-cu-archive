package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueDataTransform;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/29/2016.
 */
@GameControllerMessage(GameControllerMessageType.NET_UPDATE_TRANSFORM)
public class DataTransformController implements MessageQueueController<MessageQueueDataTransform> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTransformController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ServerObject actor, final MessageQueueDataTransform data) {
        LOGGER.warn("Not implemented");
    }
}
