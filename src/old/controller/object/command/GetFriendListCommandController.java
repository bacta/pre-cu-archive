package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatOnGetFriendsList;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(id = 0xEF21F9CE)
public class GetFriendListCommandController implements CommandController {
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Override
    public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {

        client.sendMessage(new ChatOnGetFriendsList((CreatureObject) invoker));

        logger.warn("Not fully implemented.");
    }
}
