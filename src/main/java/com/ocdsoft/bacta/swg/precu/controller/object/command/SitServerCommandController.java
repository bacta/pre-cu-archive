package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.annotations.Command;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreaturePosture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0xb719fa26)
public class SitServerCommandController implements CommandController {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {

        CreatureObject player = (CreatureObject) invoker;
        player.setPosture(CreaturePosture.SITTING);

	}
	
}
