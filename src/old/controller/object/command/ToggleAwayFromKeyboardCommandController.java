package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0x9b9fe4a8)
public class ToggleAwayFromKeyboardCommandController implements CommandController {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {

//        PlayerObject playerObject = (PlayerObject) invoker.getSlottedObject("ghost");
//
//        if(playerObject != null) {
//            MatchMakingId id = playerObject.getMatchMakingCharacterProfileId().get();
//            id.flip(MatchMakingId.B_awayFromKeyBoard);
//            playerObject.getMatchMakingCharacterProfileId().set(id);
//        }
        logger.warn("Not Implemented");
	}

	
}
