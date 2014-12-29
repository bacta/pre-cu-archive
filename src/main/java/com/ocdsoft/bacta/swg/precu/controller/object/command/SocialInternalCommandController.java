package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.object.Emote;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

@Command(id = 0x32CF1BEE)
public class SocialInternalCommandController implements CommandController {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {
		
		StringTokenizer tokenizer = new StringTokenizer(params);
		
		try {


			long targetId = Long.valueOf(tokenizer.nextToken());
			int emoteid = Integer.valueOf(tokenizer.nextToken());
			int unk1 = Integer.valueOf(tokenizer.nextToken());
			int unk2 = Integer.valueOf(tokenizer.nextToken()); //find out what this is...

            Emote emote = new Emote(invoker, invoker, targetId, emoteid, unk2 == 0 ? false : true);
            invoker.broadcastMessage(emote, true);

        } catch (Exception e) {
			logger.error("Unknown Error", e);
		}
		
	}
}
