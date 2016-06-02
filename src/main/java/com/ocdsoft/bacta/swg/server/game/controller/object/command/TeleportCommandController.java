package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import com.ocdsoft.bacta.swg.shared.util.CommandParamsIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@QueuesCommand("teleport")
public final class TeleportCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(TeleportCommandController.class);

    @Inject
    public TeleportCommandController() {
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        final CommandParamsIterator iterator = new CommandParamsIterator(params);

        final Vector vector = iterator.getVector();

        LOGGER.debug("Teleporting {} with target {} to {}", actor.getNetworkId(), target.getNetworkId(), vector.getDebugString());
    }
}