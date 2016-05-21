package com.ocdsoft.bacta.swg.server.controller.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.swg.server.message.game.client.ConnectPlayerResponseMessage;
import com.ocdsoft.bacta.swg.shared.network.messages.chat.ChatAvatarId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.message.game.client.ConnectPlayerMessage;

@MessageHandled(handles = ConnectPlayerMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ConnectPlayerMessageController implements GameNetworkMessageController<ConnectPlayerMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectPlayerMessageController.class);
    private final BactaConfiguration configuration;

    @Inject
    public ConnectPlayerMessageController(
            BactaConfiguration configuration) {

        this.configuration = configuration;
    }
    @Override
    public void handleIncoming(SoeUdpConnection connection, ConnectPlayerMessage message) {

        String game = configuration.getStringWithDefault("Bacta/GameServer", "Game", "SWG");
        String serverName = configuration.getStringWithDefault("Bacta/GameServer", "Name", "Bacta");
        String firstName = connection.getCurrentCharName().split(" ", 2)[0];

        ChatAvatarId avatarId = new ChatAvatarId(game, serverName, firstName);

//        ChatServerAgent agent = serverAgentFactory.create(
//                avatarId, new ChatServerAgentHandler(client));
//        client.setChatServerAgent(agent);

        //agent.connect();

        //agent.login("test"); //TODO: Password for chat accounts?

        connection.sendMessage(new ConnectPlayerResponseMessage(0));    }
}

