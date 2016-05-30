package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QueuesCommand("addfriend")
public class AddFriendCommandQueueController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(AddFriendCommandQueueController.class);

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        LOGGER.warn("This command not implemented");
    }
}
