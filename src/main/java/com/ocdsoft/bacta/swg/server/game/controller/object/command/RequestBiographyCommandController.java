package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.biography.BiographyService;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@QueuesCommand("requestBiography")
public final class RequestBiographyCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(RequestBiographyCommandController.class);

    private final BiographyService biographyService;

    @Inject
    public RequestBiographyCommandController(final BiographyService biographyService) {
        this.biographyService = biographyService;
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {

        if (target == null) {
            LOGGER.error("Received a request for biography with no target set.");
        } else {
            final CreatureObject creatureObject = target.asCreatureObject();

            if (creatureObject == null) {
                LOGGER.error("Request for biography of non creature object.");
            } else {
                biographyService.requestBiography(actor.getNetworkId(), creatureObject);
            }
        }
    }
}