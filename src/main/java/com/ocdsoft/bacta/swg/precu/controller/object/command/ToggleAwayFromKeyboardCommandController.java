package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id=0x9b9fe4a8)
public class ToggleAwayFromKeyboardCommandController implements CommandController {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {

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
