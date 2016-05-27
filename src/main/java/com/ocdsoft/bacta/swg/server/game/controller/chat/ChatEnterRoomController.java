package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.message.GameClientMessage;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatEnterRoom;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatError;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatOnEnteredRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/23/2016.
 */
@MessageHandled(handles = ChatEnterRoom.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class ChatEnterRoomController implements GameNetworkMessageController<ChatEnterRoom> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatEnterRoomController.class);

    private final ServerObjectService serverObjectService;
    private final GameChatService gameChatService;
    private final GameNetworkMessageSerializer gameNetworkMessageSerializer;

    @Inject
    public ChatEnterRoomController(final ServerObjectService serverObjectService,
                                   final GameNetworkMessageSerializer gameNetworkMessageSerializer,
                                   final GameChatService gameChatService) {
        this.serverObjectService = serverObjectService;
        this.gameChatService = gameChatService;
        this.gameNetworkMessageSerializer = gameNetworkMessageSerializer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatEnterRoom message) throws Exception {
        //Validate that they can enter the room, then send them a response.
        ChatError result = ChatError.SUCCESS;

        //Get the server object
        //Cast to creature
        //Check if allowed to enter room
        final ServerObject serverObject = serverObjectService.get(connection.getCurrentNetworkId());
        final CreatureObject creatureObject = serverObject != null ? serverObject.asCreatureObject() : null;

        if (creatureObject != null) {
            result = gameChatService.isAllowedToEnterRoom(creatureObject, message.getRoomName());
        } else {
            result = ChatError.INVALID_OBJECT;
        }

        if (result == ChatError.SUCCESS) {
            //Forward to chat server if chat connection available.
            final boolean chatServerAvailable = true;

            if (chatServerAvailable) {
                //Wrap the incoming message in a GameClientMessage to relay to the chat server.
                final long[] distributionList = new long[]{connection.getCurrentNetworkId()};
                final ByteBuffer messageBuffer = gameNetworkMessageSerializer.writeToBuffer(message);
                final GameClientMessage clientMessage = new GameClientMessage(distributionList, true, messageBuffer);
                gameChatService.sendToChatServer(clientMessage);
            } else {
                final ChatOnEnteredRoom fail = new ChatOnEnteredRoom(message.getSequence(), ChatError.CHAT_SERVER_UNAVAILABLE.value, 0, ChatAvatarId.EMPTY);
                connection.sendMessage(fail);
            }

        } else {
            //Tell them why they failed to enter the room.
            final ChatOnEnteredRoom fail = new ChatOnEnteredRoom(message.getSequence(), result.value, 0, ChatAvatarId.EMPTY);
            connection.sendMessage(fail);
        }
    }
}