package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatChangeIgnoreStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/30/2016.
 */
@MessageHandled(handles = ChatChangeIgnoreStatus.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(ConnectionRole.WHITELISTED)
public class ChatChangeIgnoreStatusController implements GameNetworkMessageController<ChatChangeIgnoreStatus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatChangeIgnoreStatusController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatChangeIgnoreStatusController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatChangeIgnoreStatus message) throws Exception {
        LOGGER.debug("Handling change igore status request");

        final long avatarNetworkId = chatServer.getNetworkIdByAvatarId(message.getCharacterId());

        if (message.isIgnore()) {
            chatServer.addIgnore(avatarNetworkId, message.getSequence(), message.getFriendId());
        } else {
            chatServer.removeIgnore(avatarNetworkId, message.getSequence(), message.getFriendId());
        }
    }
}
