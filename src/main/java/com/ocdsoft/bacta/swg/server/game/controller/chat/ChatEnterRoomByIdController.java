package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatEnterRoom;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatEnterRoomById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Both of these messages need to be handled by the GameServer before being sent to the ChatServer.
 * The GameServer may have some logic to validate the enter room request.
 */
@MessageHandled(handles = {ChatEnterRoom.class, ChatEnterRoomById.class})
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ChatEnterRoomByIdController implements GameNetworkMessageController<GameNetworkMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatEnterRoomByIdController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GameNetworkMessage message) {
        LOGGER.warn("This controller is not implemented");
    }
}

