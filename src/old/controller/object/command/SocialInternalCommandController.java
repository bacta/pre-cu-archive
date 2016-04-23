package com.ocdsoft.bacta.swg.precu.controller.game.object.command;


import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.message.game.object.Emote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

@Command(id = 0x32CF1BEE)
public class SocialInternalCommandController implements CommandController {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

	@Override
	public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
		
		StringTokenizer tokenizer = new StringTokenizer(params);
		
		try {


			long targetId = Long.valueOf(tokenizer.nextToken());
			int emoteid = Integer.valueOf(tokenizer.nextToken());
			int unk1 = Integer.valueOf(tokenizer.nextToken());
			int unk2 = Integer.valueOf(tokenizer.nextToken()); //find out what this is...

            Emote emote = new Emote(invoker, invoker, targetId, emoteid, unk2 == 0 ? false : true);
            invoker.broadcastMessage(emote, true);

        } catch (Exception e) {
			logger.error("UNKNOWN Error", e);
		}
		
	}
}
