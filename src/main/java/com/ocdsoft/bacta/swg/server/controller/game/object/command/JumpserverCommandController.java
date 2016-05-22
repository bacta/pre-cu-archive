package com.ocdsoft.bacta.swg.server.controller.game.object.command;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.CommandController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.message.game.object.command.Jumpserver;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(command = "Jumpserver", handles = Jumpserver.class)
public class JumpserverCommandController implements CommandController<TangibleObject> {

    private static Logger LOGGER = LoggerFactory.getLogger(JumpserverCommandController.class);

    @Override
    public void handleCommand(final SoeUdpConnection connection, final TangibleObject invoker, final TangibleObject target, final String params) {
        LOGGER.warn("This command not implemented");
    }

}
