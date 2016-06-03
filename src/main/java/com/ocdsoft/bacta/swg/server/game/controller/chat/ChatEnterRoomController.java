package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.shared.chat.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Both of these messages need to be handled by the GameServer before being sent to the ChatServer.
 * The GameServer may have some logic to validate the enter room request.
 */
@MessageHandled(handles = {ChatEnterRoom.class, ChatEnterRoomById.class})
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public final class ChatEnterRoomController implements GameNetworkMessageController<GameNetworkMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatEnterRoomController.class);

    private final ServerObjectService serverObjectService;
    private final GameChatService chatService;

    @Inject
    public ChatEnterRoomController(final ServerObjectService serverObjectService,
                                   final GameChatService chatService) {
        this.serverObjectService = serverObjectService;
        this.chatService = chatService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GameNetworkMessage message) throws Exception {

        final String roomName;
        final int sequenceId;

        if (ChatEnterRoomById.class.equals(message.getClass())) {
            final ChatEnterRoomById msg = (ChatEnterRoomById) message;
            roomName = msg.getRoomName();
            sequenceId = msg.getSequence();
        } else if (ChatEnterRoom.class.equals(message.getClass())) {
            final ChatEnterRoom msg = (ChatEnterRoom) message;
            roomName = msg.getRoomName();
            sequenceId = msg.getSequence();
        } else {
            LOGGER.error("Somehow handling message type that we can't handle.");
            return;
        }

        //Game server is supposed to determine if they are allowed to enter the room first.
        final long networkId = connection.getCurrentNetworkId();
        final ServerObject serverObject = serverObjectService.get(networkId);

        if (serverObject == null) {
            LOGGER.error("Invalid object requested to enter room.");
            return;
        }

        final CreatureObject creatureObject = serverObject.asCreatureObject();
        final ChatResult result = chatService.isAllowedToEnterRoom(creatureObject, roomName);

        if (result == ChatResult.SUCCESS) {
            chatService.forwardToChatServer(networkId, message);
        } else {
            LOGGER.debug("Failed to enter room {} because {}.", roomName, result);
            final ChatOnEnteredRoom fail = new ChatOnEnteredRoom(sequenceId, result, 0, ChatAvatarId.EMPTY);
            connection.sendMessage(fail);
        }
    }
}

