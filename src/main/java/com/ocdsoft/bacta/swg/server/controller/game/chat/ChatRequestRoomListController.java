package com.ocdsoft.bacta.swg.server.controller.game.chat;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatRequestRoomList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ChatRequestRoomList.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ChatRequestRoomListController implements GameNetworkMessageController<ChatRequestRoomList> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatRequestRoomListController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatRequestRoomList message) {
        LOGGER.warn("This controller is not implemented");
        //TODO: We want to wrap this in GameClientMessage and forward to the chat server.
    }
}

