package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatServerOnline;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/28/2016.
 * <p>
 * ChatServer->GameServer message that tells the GameServer that the ChatServer has come online.
 */
@MessageHandled(handles = ChatServerOnline.class)
@ConnectionRolesAllowed(value = ConnectionRole.WHITELISTED)
public class ChatServerOnlineController implements GameNetworkMessageController<ChatServerOnline> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServerOnlineController.class);

    private final GameChatService chatService;
    private final GameServerState serverState;

    @Inject
    public ChatServerOnlineController(final GameChatService chatService, final GameServerState serverState) {
        this.chatService = chatService;
        this.serverState = serverState;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatServerOnline message) throws Exception {
        LOGGER.info("Received notification that Chat Server is online.");

        this.chatService.setChatServerConnection(connection);
        //TODO: Fix the planet name - are we supposed to call this once per planet?
        this.chatService.createSystemRooms(serverState.getClusterServer().getName(), "tatooine");
    }
}