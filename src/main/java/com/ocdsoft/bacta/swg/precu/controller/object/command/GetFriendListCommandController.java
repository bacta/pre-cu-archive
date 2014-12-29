package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnGetFriendsList;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.shared.annotations.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id = 0xEF21F9CE)
public class GetFriendListCommandController implements CommandController {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Override
    public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {

        client.sendMessage(new ChatOnGetFriendsList((CreatureObject) invoker));

        logger.warn("Not fully implemented.");
    }
}
