package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatCreateRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ChatCreateRoom.class, type = ServerType.CHAT)
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
public class ChatCreateRoomController implements GameNetworkMessageController<ChatCreateRoom> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatCreateRoomController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatCreateRoomController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatCreateRoom message) {
        chatServer.createRoom(
                connection.getCurrentNetworkId(),
                message.getSequence(),
                message.getRoomName(),
                message.isModeratedRoom(),
                message.isPublicRoom(),
                message.getRoomTitle());
    }
}

