package com.ocdsoft.bacta.swg.precu.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.annotations.Command;
import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnAddFriend;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnChangeFriendStatus;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnGetFriendsList;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.conf.BactaConfiguration;
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
    public void handleCommand(GameClient client, TangibleObject invoker, TangibleObject target, String params) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(params);
            String name = tokenizer.nextToken();

            ChatServerAgent agent = client.getChatServerAgent();

            ChatAvatarId avatarId = agent.getAvatarId();
            ChatAvatarId friendAvatarId = new ChatAvatarId(
                    configuration.getStringWithDefault("Bacta/GameServer", "Game", "SWG"),
                    configuration.getStringWithDefault("Bacta/GameServer", "Name", "Bacta"),
                    name);

            //agent.addFriend(friendAvatarId);

            //Try to add the player to the friends list via the ChatServerAgent.

            //PlayerObject ghost = (PlayerObject) invoker.getSlottedObject("ghost");

            client.sendMessage(new ChatOnAddFriend());
            client.sendMessage(new ChatOnChangeFriendStatus(invoker.getNetworkId()));

            //ghost.getFriendList().add(friendAvatarId.getUsername());
            //ghost.getFriendList().replaceAll(Arrays.asList(new String[]{
            //        name
            //}));

            client.sendMessage(new ChatOnGetFriendsList((CreatureObject) invoker));

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
