package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameClientMessage;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.swg.server.chat.message.ChatAvatarConnected;
import com.ocdsoft.bacta.swg.server.chat.message.ChatServerOnline;
import com.ocdsoft.bacta.swg.shared.chat.ChatRoomData;
import com.ocdsoft.bacta.swg.shared.chat.ChatRoomTypes;
import com.ocdsoft.bacta.swg.shared.chat.messages.*;
import com.ocdsoft.bacta.swg.shared.foundation.NetworkIdUtil;
import gnu.trove.TCollections;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by crush on 5/23/2016.
 */
@Singleton
public final class SwgChatServer implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwgChatServer.class);

    private final GameNetworkMessageSerializer serializer;

    private final ChatAvatarId systemAvatarId;
    private final String baseName;
    private final TObjectLongMap<ChatAvatarId> avatarIdToNetworkIdMap;
    private final TLongObjectMap<ChatAvatarId> networkIdToAvatarIdMap;

    private final Map<String, ChatRoom> chatRoomMap;
    private final AtomicInteger lastRoomId;

    private final TLongSet connectedPlayers;

    private SoeUdpConnection gameServerConnection;

    private final ChatServerConfiguration configuration;

    @Inject
    public SwgChatServer(final ChatServerConfiguration configuration,
                         final GameNetworkMessageSerializer serializer) {

        this.configuration = configuration;

        this.serializer = serializer;
        this.networkIdToAvatarIdMap = TCollections.synchronizedMap(new TLongObjectHashMap<>());
        this.avatarIdToNetworkIdMap = TCollections.synchronizedMap(new TObjectLongHashMap<>());
        this.connectedPlayers = new TLongHashSet();
        this.chatRoomMap = new ConcurrentHashMap<>();

        this.systemAvatarId = configuration.getSystemAvatarId();

        //Put the system avatar in the networkIdToAvatarIdMap and avatarIdtoNetworkIdMap with NetworkIdUtil.INVALID.
        this.networkIdToAvatarIdMap.put(NetworkIdUtil.INVALID, systemAvatarId);
        this.avatarIdToNetworkIdMap.put(systemAvatarId, NetworkIdUtil.INVALID);

        this.baseName = String.format("%s.%s", systemAvatarId.getGameCode(), systemAvatarId.getCluster()).toLowerCase();

        this.lastRoomId = new AtomicInteger(0);
    }

    public void notifyGameServerOnline(final SoeUdpConnection connection) {
        try {
            LOGGER.info("Received notification that game server came online. Sending ChatServerOnline to game server.");
            this.gameServerConnection = connection;

            final InetSocketAddress chatServerAddress = configuration.getBindAddress();
            this.gameServerConnection.sendMessage(new ChatServerOnline(chatServerAddress.getHostName(), chatServerAddress.getPort()));

        } catch (UnknownHostException ex) {
            LOGGER.error("Unable to get the address for chat server: {}", ex.getMessage());
        }
    }

    /**
     * Connects a player to the chat server, storing a connection to the game server from which they are communicating, their
     * BactaId identifying them on the Bacta Network, and the networkId associated with their creature.
     *
     * @param connection    The connection back to the game server, through which the player is sending messages.
     * @param bactaId       The BactaId for the player on the Bacta Network.
     * @param characterName The name of the character - first name only.
     * @param networkId     The network Id of the Creature Object the player controls.
     * @param isSecure      If they are connected on a GodClient, and have been authenticated as such.
     * @param isSubscribed  If they are subscribed to something?
     */
    public void connectPlayer(final SoeUdpConnection connection, final int bactaId, final String characterName, final long networkId, final boolean isSecure, final boolean isSubscribed) {
        LOGGER.info("Player connecting to chat server from {}:{} with name {}.",
                connection.getRemoteAddress().getAddress().getHostAddress(),
                connection.getRemoteAddress().getPort(),
                characterName);

        connectedPlayers.add(networkId);

        //TODO: Destroy any pending ChatDestroyAvatar request for this avatar name.

        final ChatAvatarId avatarId = makeChatAvatarId(characterName);

        final ChatAvatarConnected avatarConnected = new ChatAvatarConnected(networkId);
        connection.sendMessage(avatarConnected);

        if (!avatarId.equals(systemAvatarId)) {
            avatarIdToNetworkIdMap.put(avatarId, networkId);
            networkIdToAvatarIdMap.put(networkId, avatarId);

            //TODO: Send any pending messages for this avatar.

            //clearQueuedHeadersForAvatar
            //RequestGetPersistentHeaders(avatarId, null)
        }

        final ChatOnConnectAvatar onAvatarConnected = new ChatOnConnectAvatar();
        sendToClient(networkId, onAvatarConnected);
    }

    /**
     * Requests all rooms which the given chat user can see. This includes rooms which are public,
     * they have created, or to which they have been invited.
     *
     * @param connection The connection used by the incoming request.
     * @param networkId  The networkId for the user who is requesting rooms.
     */
    public void requestRoomList(final SoeUdpConnection connection, final long networkId) {
        final ChatAvatarId requestingAvatar = networkIdToAvatarIdMap.get(networkId);

        if (requestingAvatar == null) {
            LOGGER.warn("Invalid avatar with networkId {} tried to request room list.", networkId);
            return;
        }

        final List<ChatRoomData> rooms = new ArrayList<>();

        //TODO: Why not rooms of which they are the owner, admin, or moderator?? Investigate.
        chatRoomMap.values().stream()
                .filter(room -> room.isPublic() || room.isCreator(requestingAvatar) || room.isInvited(requestingAvatar))
                .forEach(room -> rooms.add(room.createChatRoomData()));

        LOGGER.debug("Sending {} rooms to {}({})",
                rooms.size(), requestingAvatar.getFullName(), Long.toHexString(networkId));

        final ChatRoomList response = new ChatRoomList(rooms);
        sendToClient(connection, networkId, response);
    }

    /**
     * A request to create a new chat room.
     *
     * @param connection
     * @param networkId
     * @param sequence
     * @param roomName
     * @param isModerated
     * @param isPublic
     * @param title
     */
    public void createRoom(final SoeUdpConnection connection,
                           final long networkId,
                           final int sequence,
                           final String roomName,
                           final boolean isModerated,
                           final boolean isPublic,
                           final String title) {

        //Get the would be owner of the new room if it is created.
        final ChatAvatarId ownerId = networkIdToAvatarIdMap.get(networkId);

        if (ownerId == null) {
            LOGGER.warn("Could not find avatar with networkId {} when trying to create room.", networkId);
            return;
        }

        final String lowerRoomName = roomName.toLowerCase();

        //If the room name is invalid, then it's either a programming error or a hacking attempt - ignore it.
        if (!isValidRoomName(lowerRoomName)) {
            LOGGER.warn("Discarding invalid createRoom request with room name {} attempted by {}({}).",
                    lowerRoomName,
                    ownerId.getFullName(),
                    Long.toHexString(networkId));
            return;
        }

        //Make sure it's not a base room, and that it starts with the base path.
        if (lowerRoomName.equals(baseName) || !lowerRoomName.startsWith(baseName)) {
            LOGGER.error("Room {} did not start with the required base name of {}.", lowerRoomName, baseName);
            return;
        }

        //At this point, we are handling a valid request, and should start notifying the client if there are any other
        //types of errors.
        LOGGER.debug("Creating {}, {} room for {} with title '{}' and path '{}'.",
                isPublic ? "public" : "private",
                isModerated ? "moderated" : "unmoderated",
                ownerId.getFullName(),
                title,
                lowerRoomName);

        ChatError error = ChatError.SUCCESS;
        ChatRoomData roomData = ChatRoomData.EMPTY;

        //Check if the room already exists...
        if (!chatRoomMap.containsKey(lowerRoomName)) {

            final int partitions = StringUtils.countMatches(lowerRoomName, ".");

            if (partitions > 1) {
                final int lastPartitionIndex = lowerRoomName.lastIndexOf('.');

                //We don't need to check for lastPartitionIndex to be -1 because we've already said there are at least 1 partitions.
                final String parentRoom = lowerRoomName.substring(0, lastPartitionIndex);

                //Check if the parent room exists. If not, create it.
                if (!chatRoomMap.containsKey(parentRoom))
                    createRoom(connection, networkId, 0, parentRoom, isModerated, isPublic, "");
            }

            //Setup the attributes for the room.
            int attributes = 0;

            //If the room is owned by the system avatar, and contains the word system, then it's a persistent system room.
            if (systemAvatarId.equals(ownerId) && lowerRoomName.contains("system"))
                attributes = ChatRoomAttributes.setAttribute(attributes, ChatRoomAttributes.PERSISTENT);

            if (isModerated)
                attributes = ChatRoomAttributes.setAttribute(attributes, ChatRoomAttributes.MODERATED);

            if (!isPublic)
                attributes = ChatRoomAttributes.setAttribute(attributes, ChatRoomAttributes.PRIVATE);

            //Finally, create the chat room, and put it into the chat room map.
            final ChatRoom chatRoom = new ChatRoom(lastRoomId.incrementAndGet(), ownerId, lowerRoomName, title, attributes);
            this.chatRoomMap.put(lowerRoomName, chatRoom);

            roomData = chatRoom.createChatRoomData();

            //TODO: Persist the chat room now.

            LOGGER.debug("Created room {} for {}.", lowerRoomName, ownerId.getFullName());

            //Place the system avatar in the room, as it should be in every room.
            putSystemAvatarInRoom(lowerRoomName);
        } else {
            LOGGER.debug("Declined creation of room {} because it already exists.", lowerRoomName);
            error = ChatError.ROOM_ALREADY_EXISTS;
        }

        //Send the room to the client.
        final ChatOnCreateRoom response = new ChatOnCreateRoom(sequence, error.value, roomData);
        connection.sendMessage(response);
    }

    public void putSystemAvatarInRoom(final String roomName) {
        final long networkId = avatarIdToNetworkIdMap.get(systemAvatarId);
        enterRoom(networkId, 0, roomName);
    }

    /**
     * Attempts to enter a chat room. If unable to enter the room, ChatOnEnteredRoom will be returned with an error message.
     * Otherwise, all occupants of the room will receive the ChatOnEnteredRoom response alerting them that someone has
     * joined the room.
     *
     * @param networkId The networkId of the avatar requesting to enter the room.
     * @param sequence  The transmission sequence number.
     * @param roomName  The name/path of the room.
     */
    public void enterRoom(final long networkId, final int sequence, final String roomName) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);

        if (avatarId == null) {
            LOGGER.warn("Could not find avatar with networkId {} when trying to enter room.", networkId);
            return;
        }

        final String lowerRoomName = roomName.toLowerCase();

        ChatError error = ChatError.SUCCESS;
        int roomId = 0;

        final ChatRoom chatRoom = chatRoomMap.get(lowerRoomName);

        if (chatRoom != null) {
            roomId = chatRoom.getId();

            final int lastPartitionIndex = lowerRoomName.lastIndexOf('.');

            if (lastPartitionIndex != -1) {
                final String leaf = lowerRoomName.substring(0, lastPartitionIndex);

                //Don't allow the player to enter or leave the root system rooms.
                if (!leaf.equalsIgnoreCase(ChatRoomTypes.SYSTEM)) {

                    //Send room data to player in case they didn't already know about the room...
                    final ChatRoomList roomList = new ChatRoomList(Collections.singletonList(chatRoom.createChatRoomData()));
                    sendToClient(networkId, roomList);

                    //Tell the client the results of the request.
                    final ChatOnEnteredRoom enteredRoom = new ChatOnEnteredRoom(sequence, error.value, roomId, avatarId);
                    sendToClient(networkId, enteredRoom);

                    LOGGER.info("{} entered room {} successfully.", avatarId.getFullName(), lowerRoomName);

                    //Tell everyone else in the room.
                    final Iterator<ChatAvatarId> occupants = chatRoom.getAvatarsIterator();

                    while (occupants.hasNext()) {
                        final ChatAvatarId occupantId = occupants.next();
                        final long occupantNetworkId = avatarIdToNetworkIdMap.get(occupantId);

                        if (occupantNetworkId != NetworkIdUtil.INVALID && occupantNetworkId != networkId) {
                            sendToClient(occupantNetworkId, enteredRoom);
                        }
                    }

                    return;
                }
            }

            error = ChatError.ROOM_UNKNOWN_FAILURE;
        } else {
            error = ChatError.ADDRESS_NOT_ROOM;
        }

        //If we were unable to place the request, then we need to inform the client now.
        final ChatOnEnteredRoom fail = new ChatOnEnteredRoom(sequence, error.value, roomId, avatarId);
        sendToClient(networkId, fail);
    }

    /**
     * Looks up the connection associated with a networkId, and sends the message there. If no connection exists, then
     * the message will be placed into a pending queue until a connection does exist.
     *
     * @param networkId The NetworkId of the player to whom to send the message.
     * @param gnm       The message to send.
     */
    public void sendToClient(final long networkId, final GameNetworkMessage gnm) {
        sendToClient(gameServerConnection, networkId, gnm);
    }

    public void sendToClient(final SoeUdpConnection connection, final long networkId, final GameNetworkMessage gnm) {
        final ByteBuffer messageBuffer = serializer.writeToBuffer(gnm);

        final GameClientMessage gameClientMessage = new GameClientMessage(
                new long[]{networkId}, true, messageBuffer);

        if (connection != null) {
            connection.sendMessage(gameClientMessage);
        } else {
            //TODO: Implement pending queue.
            LOGGER.warn("Tried to send message of type {} to client, but the connection was invalid.",
                    gnm.getClass().getSimpleName());
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {

    }

    private static boolean isValidRoomName(final String roomName) {
        //Can't be empty and can't start with a partition separator.
        if (roomName.isEmpty() || roomName.charAt(0) == '.')
            return false;

        //Can't contain consecutive partition separators and can't contain spaces.
        char lastChar = '\0';
        for (int i = 0, size = roomName.length(); i < size; ++i) {
            final char c = roomName.charAt(i);

            if (c == ' ') return false;
            if (c == '.' && lastChar == '.') return false;

            lastChar = c;
        }

        return true;
    }

    private ChatAvatarId makeChatAvatarId(final String characterName) {
        return new ChatAvatarId(
                systemAvatarId.getGameCode(),
                systemAvatarId.getCluster(),
                characterName.toLowerCase()); //Make sure the name is lowercase.
    }
}
