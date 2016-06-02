package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@QueuesCommand("handleClientLogin")
public final class HandleClientLoginCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(HandleClientLoginCommandController.class);

    @Inject
    public HandleClientLoginCommandController() {
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        LOGGER.error("Not implemented");
    }
}