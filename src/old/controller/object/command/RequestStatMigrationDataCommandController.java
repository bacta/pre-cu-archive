package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.player.StatMigrationTargetsMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0x7afca539)
public class RequestStatMigrationDataCommandController implements CommandController {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {

        CreatureObject creatureObject = (CreatureObject)invoker;

        if (creatureObject == null)
            return;

        client.sendMessage(new StatMigrationTargetsMessage((creatureObject)));
		
	}
	
}
