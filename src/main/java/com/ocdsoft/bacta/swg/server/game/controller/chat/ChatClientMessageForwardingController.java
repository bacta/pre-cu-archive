package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.shared.chat.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/28/2016.
 * <p>
 * Handles incoming SwgClient->GameServer chat messages, wraps them in a GameClientMessage, and forward them directly
 * to the ChatServer.
 */
@MessageHandled(handles = {
        ChatAddFriend.class,
        ChatAddModeratorToRoom.class,
        ChatBanAvatarFromRoom.class,
        ChatCreateRoom.class,
        ChatDeletePersistentMessage.class,
        ChatDeleteAllPersistentMessages.class,
        ChatDestroyRoom.class,
        ChatInstantMessageToCharacter.class,
        ChatInviteAvatarToRoom.class,
        ChatKickAvatarFromRoom.class,
        ChatRemoveAvatarFromRoom.class,
        ChatRemoveFriend.class,
        ChatRemoveModeratorFromRoom.class,
        ChatRequestPersistentMessage.class,
        ChatRequestRoomList.class,
        ChatSendToRoom.class,
        ChatUninviteFromRoom.class,
        ChatUnbanAvatarFromRoom.class})
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public final class ChatClientMessageForwardingController implements GameNetworkMessageController<GameNetworkMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClientMessageForwardingController.class);


    private final GameChatService gameChatService;

    @Inject
    public ChatClientMessageForwardingController(final GameChatService gameChatService) {
        this.gameChatService = gameChatService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GameNetworkMessage message) throws Exception {
        gameChatService.forwardToChatServer(connection.getCurrentNetworkId(), message);
    }
}