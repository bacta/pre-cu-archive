package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ocdsoft.bacta.soe.controller.CommandController;
import com.ocdsoft.bacta.swg.server.game.message.object.command.Selectdraftschematic;

@MessageHandled(handles = Selectdraftschematic.class)
public class SelectdraftschematicCommandController implements CommandController<TangibleObject> {

	private static Logger LOGGER = LoggerFactory.getLogger(SelectdraftschematicCommandController.class);

	@Override
	public void handleCommand(final SoeUdpConnection connection, final TangibleObject invoker, final TangibleObject target, final String params) {
		LOGGER.warn("This command not implemented");
	}
	
}