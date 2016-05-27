package com.ocdsoft.bacta.swg.server.game.controller.game.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.message.GameClientMessage;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.swg.server.game.service.chat.GameChatService;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatInstantMessageToCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/23/2016.
 */
@MessageHandled(handles = ChatInstantMessageToCharacter.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class ChatInstantMessageToCharacterController implements GameNetworkMessageController<ChatInstantMessageToCharacter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatInstantMessageToCharacterController.class);

    private final GameNetworkMessageSerializer gameNetworkMessageSerializer;
    private final GameChatService gameChatService;

    @Inject
    public ChatInstantMessageToCharacterController(final GameNetworkMessageSerializer gameNetworkMessageSerializer,
                                                   final GameChatService gameChatService) {
        this.gameNetworkMessageSerializer = gameNetworkMessageSerializer;
        this.gameChatService = gameChatService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatInstantMessageToCharacter message) throws Exception {
        LOGGER.info("Received incoming instant message to {} with message {}.",
                message.getCharacterName().getFullName(),
                message.getMessage());

        final long[] distributionList = new long[]{connection.getCurrentNetworkId()};
        final ByteBuffer internalMessage = gameNetworkMessageSerializer.writeToBuffer(message);

        final GameClientMessage gcm = new GameClientMessage(distributionList, true, internalMessage);
        gameChatService.sendToChatServer(gcm);
    }
}