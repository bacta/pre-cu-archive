package com.ocdsoft.bacta.swg.precu.controller.game.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatOnAddFriend;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatOnChangeFriendStatus;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatOnGetFriendsList;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

@Command(id = 0x2A2357ED)
public class AddFriendCommandController implements CommandController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BactaConfiguration configuration;

    @Inject
    public AddFriendCommandController(BactaConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void handleCommand(SoeUdpConnection connection, TangibleObject invoker, TangibleObject target, String params) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(params);
            String name = tokenizer.nextToken();

            ChatServerAgent agent = connection.getChatServerAgent();

            ChatAvatarId avatarId = agent.getAvatarId();
            ChatAvatarId friendAvatarId = new ChatAvatarId(
                    configuration.getStringWithDefault("Bacta/GameServer", "Game", "SWG"),
                    configuration.getStringWithDefault("Bacta/GameServer", "Name", "Bacta"),
                    name);

            //agent.addFriend(friendAvatarId);

            //Try to add the player to the friends list via the ChatServerAgent.

            //PlayerObject ghost = (PlayerObject) invoker.getSlottedObject("ghost");

            connection.sendMessage(new ChatOnAddFriend());
            connection.sendMessage(new ChatOnChangeFriendStatus(invoker.getNetworkId()));

            //ghost.getFriendList().add(friendAvatarId.getUsername());
            //ghost.getFriendList().replaceAll(Arrays.asList(new String[]{
            //        name
            //}));

            connection.sendMessage(new ChatOnGetFriendsList((CreatureObject) invoker));

            //ProsePackage prose = new ProsePackage(new StringId("cmnty", "friend_added"));
            //prose.setTarget(new ProsePackageParticipant(name));

            //ChatSystemMessage sysmsg = new ChatSystemMessage(new OutOfBand(prose));
            //client.sendMessage(sysmsg);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
