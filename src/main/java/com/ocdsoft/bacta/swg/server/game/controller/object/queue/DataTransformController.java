package com.ocdsoft.bacta.swg.server.game.controller.object.queue;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.server.game.controller.object.MessageQueueController;
import com.ocdsoft.bacta.swg.server.game.creature.CreatureObjectService;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueDataTransform;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/29/2016.
 */
@GameControllerMessage(GameControllerMessageType.NET_UPDATE_TRANSFORM)
public class DataTransformController implements MessageQueueController<MessageQueueDataTransform> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTransformController.class);

    private final ServerObjectService serverObjectService;
    private final CreatureObjectService creatureObjectService;

    @Inject
    public DataTransformController(final ServerObjectService serverObjectService,
                                   final CreatureObjectService creatureObjectService) {
        this.serverObjectService = serverObjectService;
        this.creatureObjectService = creatureObjectService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ServerObject actor, final int flags, final float value, final MessageQueueDataTransform data) {
    }
}