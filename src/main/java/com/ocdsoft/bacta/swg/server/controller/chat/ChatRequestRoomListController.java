package com.ocdsoft.bacta.swg.server.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatRequestRoomList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/23/2016.
 */
@MessageHandled(handles = ChatRequestRoomList.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class ChatRequestRoomListController implements GameNetworkMessageController<ChatRequestRoomList> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRequestRoomListController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatRequestRoomListController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatRequestRoomList message) throws Exception {
        //chatServer.requestRoomList(networkId, connection)
    }
}