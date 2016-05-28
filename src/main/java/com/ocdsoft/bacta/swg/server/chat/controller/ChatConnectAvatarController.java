package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatConnectAvatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/21/2016.
 */

@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatConnectAvatar.class, type = ServerType.CHAT)
public class ChatConnectAvatarController implements GameNetworkMessageController<ChatConnectAvatar> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConnectAvatarController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatConnectAvatarController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatConnectAvatar message) throws Exception {
        LOGGER.debug("Received chat connect avatar request for {}", message.getCharacterName());

        chatServer.connectPlayer(
                connection,
                message.getStationId(),
                message.getCharacterName(),
                message.getCharacterId(),
                message.isSecure(),
                message.isSubscribed());
    }

}
