package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatPutAvatarInRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/29/2016.
 */
@MessageHandled(handles = ChatPutAvatarInRoom.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(value = ConnectionRole.WHITELISTED)
public class ChatPutAvatarInRoomController implements GameNetworkMessageController<ChatPutAvatarInRoom> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatPutAvatarInRoomController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatPutAvatarInRoomController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatPutAvatarInRoom message) throws Exception {
        final ChatAvatarId avatarId = chatServer.makeChatAvatarId(message.getAvatarName());

        LOGGER.debug("{} is attempting to enter room {}.", avatarId.getFullName(), message.getRoomName());

        chatServer.enterRoom(avatarId, message.getRoomName(), message.isForceCreate(), message.isCreatePrivate());
    }
}