package com.ocdsoft.bacta.swg.server.controller.game.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatAvatarConnected;
import com.ocdsoft.bacta.swg.server.message.game.client.ChatOnConnectAvatar;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.server.service.object.ServerObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/21/2016.
 * <p>
 * An avatar has connected. Find them and send them {@link ChatOnConnectAvatar}
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatAvatarConnected.class)
public class ChatAvatarConnectedController implements GameNetworkMessageController<ChatAvatarConnected> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatAvatarConnectedController.class);

    private final ServerObjectService serverObjectService;

    @Inject
    public ChatAvatarConnectedController(final ServerObjectService serverObjectService) {
        this.serverObjectService = serverObjectService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatAvatarConnected message) throws Exception {
        final long networkId = message.getCharacterId();

        //Find the client that belongs to this network id and send them the ChatOnConnectAvatar message.
        final ServerObject object = serverObjectService.get(networkId);
        final SoeUdpConnection client = object.getConnection();

        if (client != null) {
            client.sendMessage(new ChatOnConnectAvatar());
        } else {
            LOGGER.error("Could not find object with id {} for which to send ChatOnConnectAvatar message.", networkId);
        }
    }
}
