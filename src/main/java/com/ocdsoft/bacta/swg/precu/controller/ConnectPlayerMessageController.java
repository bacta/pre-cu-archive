package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgentFactory;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgentHandler;
import com.ocdsoft.bacta.swg.server.game.message.chat.ConnectPlayerMessage;
import com.ocdsoft.bacta.swg.server.game.message.zone.ConnectPlayerResponseMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.conf.BactaConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = ConnectPlayerMessage.class)
public class ConnectPlayerMessageController implements SwgMessageController<GameClient> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ChatServerAgentFactory serverAgentFactory;
    private final BactaConfiguration configuration;

    @Inject
    public ConnectPlayerMessageController(
            ChatServerAgentFactory serverAgentFactory,
            BactaConfiguration configuration) {

        this.serverAgentFactory = serverAgentFactory;
        this.configuration = configuration;
    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf data) throws Exception {
        CreatureObject creatureObject = client.getCharacter();

        String game = configuration.getStringWithDefault("Bacta/GameServer", "Game", "SWG");
        String serverName = configuration.getStringWithDefault("Bacta/GameServer", "Name", "Bacta");
        String firstName = creatureObject.getObjectName().getString().split(" ", 2)[0];

        ChatAvatarId avatarId = new ChatAvatarId(game, serverName, firstName);

        ChatServerAgent agent = serverAgentFactory.create(
                avatarId, new ChatServerAgentHandler(client));
        client.setChatServerAgent(agent);

        //agent.connect();

        //agent.login("test"); //TODO: Password for chat accounts?

        client.sendMessage(new ConnectPlayerResponseMessage());
    }
}
