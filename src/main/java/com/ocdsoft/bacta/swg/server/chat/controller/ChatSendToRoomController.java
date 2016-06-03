package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameClientMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatSendToRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@MessageHandled(handles = ChatSendToRoom.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(ConnectionRole.WHITELISTED)
public class ChatSendToRoomController implements GameClientMessageController<ChatSendToRoom> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatSendToRoomController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatSendToRoomController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final long[] distributionList, final boolean reliable, final ChatSendToRoom message, final SoeUdpConnection connection) throws Exception {
        for (final long networkId : distributionList) {
            chatServer.sendRoomMessage(networkId, message.getSequence(), message.getRoomId(), message.getMessage(), message.getOutOfBand());
        }
    }
}
