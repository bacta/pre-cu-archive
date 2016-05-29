package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatServerConnectionOpened;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/21/2016.
 *
 * This message is ChatServer->GameServer.
 *
 * It is indicating that the ChatServer has successfully opened a connection to the GameServer.
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatServerConnectionOpened.class)
public class ChatServerConnectionOpenedController implements GameNetworkMessageController<ChatServerConnectionOpened> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServerConnectionOpened.class);

    private final GameChatService chatService;
    private final GameServerState serverState;

    @Inject
    public ChatServerConnectionOpenedController(final GameChatService chatService,
                                                final GameServerState serverState) {
        this.chatService = chatService;
        this.serverState = serverState;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatServerConnectionOpened message) throws Exception {
        LOGGER.info("Chat Server has opened a connection to game server.");
        chatService.setChatServerConnection(connection);
    }
}
