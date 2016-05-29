package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameClientMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatRequestRoomList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/28/2016.
 */
@MessageHandled(handles = ChatRequestRoomList.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(ConnectionRole.WHITELISTED)
public class ChatRequestRoomListController implements GameClientMessageController<ChatRequestRoomList> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRequestRoomListController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatRequestRoomListController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final long[] distributionList, final boolean reliable, final ChatRequestRoomList message, final SoeUdpConnection connection) throws Exception {
        LOGGER.info("Incoming request for room list.");

        for (final long networkId : distributionList)
            chatServer.requestRoomList(connection, networkId);
    }
}
