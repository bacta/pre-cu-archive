package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.game.object.SpatialChat;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

@Command(id = 0x7C8D63D4)
public class SpatialChatInternalCommandController implements CommandController {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
		
		try {

            StringTokenizer tokenizer = new StringTokenizer(params);

            long targetId = Long.valueOf(tokenizer.nextToken());
            int mood2 = Integer.valueOf(tokenizer.nextToken());
            int moodid = Integer.valueOf(tokenizer.nextToken());
            int unk1 = Integer.valueOf(tokenizer.nextToken());
            int unk2 = Integer.valueOf(tokenizer.nextToken());

            String text = tokenizer.nextToken();

            while (tokenizer.hasMoreTokens())
                text += " " + tokenizer.nextToken();

            SpatialChat chat = new SpatialChat(
                    invoker.getNetworkId(),
                    invoker.getNetworkId(),
                    text,
                    targetId,
                    moodid,
                    mood2,
                    (byte) 0
            );

            invoker.broadcastMessage(chat, true);

		} catch (Exception e) {
			logger.error("UNKNOWN error", e);
		}
		
	}
}
