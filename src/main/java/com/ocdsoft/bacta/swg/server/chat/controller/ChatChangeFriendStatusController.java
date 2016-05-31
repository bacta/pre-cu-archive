package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatChangeFriendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/30/2016.
 */
@MessageHandled(handles = ChatChangeFriendStatus.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(ConnectionRole.WHITELISTED)
public class ChatChangeFriendStatusController implements GameNetworkMessageController<ChatChangeFriendStatus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatChangeFriendStatusController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatChangeFriendStatusController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ChatChangeFriendStatus message) throws Exception {
        LOGGER.debug("Handling change friend status request");

        final long avatarNetworkId = chatServer.getNetworkIdByAvatarId(message.getCharacterId());

        if (message.isAdd()) {
            chatServer.addFriend(avatarNetworkId, message.getSequence(), message.getFriendId());
        } else {
            chatServer.removeFriend(avatarNetworkId, message.getSequence(), message.getFriendId());
        }
    }
}
