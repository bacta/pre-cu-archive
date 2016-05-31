package com.ocdsoft.bacta.swg.server.game.controller.object.queue;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.server.game.controller.object.MessageQueueController;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueObjectMenuRequest;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/30/2016.
 */
@GameControllerMessage(GameControllerMessageType.OBJECT_MENU_REQUEST)
public final class ObjectMenuRequestController implements MessageQueueController<MessageQueueObjectMenuRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMenuRequestController.class);

    private final ServerObjectService serverObjectService;

    @Inject
    public ObjectMenuRequestController(final ServerObjectService serverObjectService) {
        this.serverObjectService = serverObjectService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ServerObject actor, final int flags, final float value, final MessageQueueObjectMenuRequest data) {
        final CreatureObject creatureObject = actor.asCreatureObject();

        if (creatureObject == null)
            return;

        final ServerObject targetObject = this.serverObjectService.get(data.getTargetId());

        if (targetObject == null)
            return;


    }
}