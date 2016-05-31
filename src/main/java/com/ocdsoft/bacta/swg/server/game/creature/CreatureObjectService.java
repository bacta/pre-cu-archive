package com.ocdsoft.bacta.swg.server.game.creature;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessageFlags;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueDataTransform;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.container.ContainerService;

/**
 * Created by crush on 5/30/2016.
 */
@Singleton
public final class CreatureObjectService {
    private final ContainerService containerService;

    @Inject
    public CreatureObjectService(final ContainerService containerService) {
        this.containerService = containerService;
    }

    public void handleDataTransform(final CreatureObject creatureObject, final float value, final int flags, final MessageQueueDataTransform dataTransform) {
        creatureObject.setLookAtYaw(dataTransform.getLookAtYaw(), dataTransform.isUseLookAtYaw());

        if ((flags & GameControllerMessageFlags.SOURCE_REMOTE_SERVER) != 0) {
            //If we're not contained by what we are attached to, then transform
            //updates are not relevant to the clients because our position is
            //dictated purely by our containing object.
            if (creatureObject.getAttachedTo() == ContainerService.getContainedByObject(creatureObject)) {
                //final UpdateTransformMessage msg = new UpdateTransformMessage(
                //        creatureObject,
                //        dataTransform.getSequenceNumber(),
                //        dataTransform.getTransform(),
                //        (byte)dataTransform.getSpeed(),
                //        dataTransform.getLookAtYaw(),
                //        dataTransform.isUseLookAtYaw());
                //creatureObject.sendToClientsInUpdateRange(msg, flags & GameControllerMessageFlags.RELIABLE, false);
            }
        }
    }

}
