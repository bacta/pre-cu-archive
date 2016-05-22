package com.ocdsoft.bacta.swg.server.controller.game.object.command;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.CommandController;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.object.tangible.creature.CreaturePosture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SitServerCommandController implements CommandController<TangibleObject> {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
		CreatureObject player = (CreatureObject) invoker;
		player.setPosture(CreaturePosture.SITTING);
	}
}
