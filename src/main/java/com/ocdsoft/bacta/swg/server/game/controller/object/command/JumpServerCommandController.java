package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/2/2016.
 */
@QueuesCommand("jumpServer")
public final class JumpServerCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(JumpServerCommandController.class);

    @Inject
    public JumpServerCommandController() {
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        //SOE just added an empty spot into the queue apparently for this command...
    }
}