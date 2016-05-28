package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatServerConnectionOpened;
import com.ocdsoft.bacta.swg.server.game.service.chat.GameChatService;

/**
 * Created by crush on 5/21/2016.
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatServerConnectionOpened.class)
public class ChatServerConnectionOpenedController implements GameNetworkMessageController<ChatServerConnectionOpened> {
    private final GameChatService chatService;

    @Inject
    public ChatServerConnectionOpenedController(final GameChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatServerConnectionOpened message) throws Exception {
        chatService.setChatServerConnection(connection);
    }
}
