package com.ocdsoft.bacta.swg.server.chat.controller;

import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatAvatarConnected;
import com.ocdsoft.bacta.swg.server.message.game.chat.ChatConnectAvatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/21/2016.
 */

@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatConnectAvatar.class, type = ServerType.CHAT)
public class ChatConnectAvatarController implements GameNetworkMessageController<ChatConnectAvatar> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConnectAvatarController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatConnectAvatar message) throws Exception {
        final long characterId = message.getCharacterId();
        LOGGER.warn("Not fully implemented. We aren't actually checking to connect the avatar. We are just saying they are connected.");
        connection.sendMessage(new ChatAvatarConnected(characterId));
    }

}
