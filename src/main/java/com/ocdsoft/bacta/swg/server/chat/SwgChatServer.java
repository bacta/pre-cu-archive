package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.collect.RelationshipMap;
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

    private static final int MAX_ROOM_MESSAGE_LENGTH = 512;

    private transient final GameNetworkMessageSerializer serializer;
    private transient final ChatServerConfiguration configuration;

    private transient final TLongSet connectedPlayers;
    private transient final ChatAvatarId systemAvatarId;
    private transient final String baseName;

    private transient SoeUdpConnection gameServerConnection;

    //Persistent data.
    private final TObjectLongMap<ChatAvatarId> avatarIdToNetworkIdMap;
    private final TLongObjectMap<ChatAvatarId> networkIdToAvatarIdMap;

    private final RelationshipMap<ChatAvatarId> friendRelationships;
    private final RelationshipMap<ChatAvatarId> ignoreRelationships;

    private final Map<String, ChatRoom> chatRoomMap;
    private final AtomicInteger lastRoomId;

    @Inject
    public SwgChatServer(final ChatServerConfiguration configuration,
                         final GameNetworkMessageSerializer serializer) {

        this.configuration = configuration;

        this.serializer = serializer;
        this.networkIdToAvatarIdMap = TCollections.synchronizedMap(new TLongObjectHashMap<>());
        this.avatarIdToNetworkIdMap = TCollections.synchronizedMap(new TObjectLongHashMap<>());
        this.connectedPlayers = TCollections.synchronizedSet(new TLongHashSet());
        this.chatRoomMap = new ConcurrentHashMap<>();

        //TODO: Should system avatar go through connect like soe did?
        this.systemAvatarId = configuration.getSystemAvatarId();
        this.networkIdToAvatarIdMap.put(NetworkIdUtil.INVALID, systemAvatarId);
        this.avatarIdToNetworkIdMap.put(systemAvatarId, NetworkIdUtil.INVALID);

        this.baseName = String.format("%s.%s", systemAvatarId.getGameCode(), systemAvatarId.getCluster());
        this.lastRoomId = new AtomicInteger(0);

        this.friendRelationships = new RelationshipMap<>();
        this.ignoreRelationships = new RelationshipMap<>();
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

    public long getNetworkIdByAvatarId(final ChatAvatarId avatarId) {
        return avatarIdToNetworkIdMap.get(avatarId);
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

        final ChatAvatarId avatarId = makeChatAvatarId(characterName);

        //TODO: Destroy any pending ChatDestroyAvatar request for this avatar name.

        //Add them to our set of connected players so that we know who's online/offline.
        connectedPlayers.add(networkId);

        final ChatAvatarConnected avatarConnected = new ChatAvatarConnected(networkId);
        connection.sendMessage(avatarConnected);

        if (!avatarId.equals(systemAvatarId)) {
            //Make sure we have them in our persisted NetworkId->AvatarId and AvatarId->NetworkId maps.
            avatarIdToNetworkIdMap.put(avatarId, networkId);
            networkIdToAvatarIdMap.put(networkId, avatarId);

            //TODO: Send any pending messages for this avatar.

            //clearQueuedHeadersForAvatar
            //RequestGetPersistentHeaders(avatarId, null)
        }

        final ChatOnConnectAvatar onAvatarConnected = new ChatOnConnectAvatar();
        sendToClient(networkId, onAvatarConnected);

        notifyFriendsOfLogin(avatarId);
    }

    public void disconnectPlayer(final long networkId) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);

        LOGGER.debug("Avatar [{}] disconnecting from chat server.", avatarId.getFullName());

        connectedPlayers.remove(networkId);
        notifyFriendsOfLogout(avatarId);
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
     * @param networkId
     * @param sequence
     * @param roomName
     * @param isModerated
     * @param isPublic
     * @param title
     */
    public void createRoom(final long networkId,
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
            LOGGER.warn("Discarding invalid createRoom request with room name {} attempted by {}({})",
                    lowerRoomName,
                    ownerId.getFullName(),
                    Long.toHexString(networkId));
            return;
        }

        //Make sure it's not a base room, and that it starts with the base path.
        if (lowerRoomName.equals(baseName) || !lowerRoomName.startsWith(baseName)) {
            LOGGER.error("Room '{}' did not start with the required base name of '{}'", lowerRoomName, baseName);
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

        ChatResult error = ChatResult.SUCCESS;
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
                    createRoom(networkId, 0, parentRoom, isModerated, isPublic, "");
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
            error = ChatResult.ROOM_ALREADY_EXISTS;
        }

        //Send the room to the client.
        final ChatOnCreateRoom response = new ChatOnCreateRoom(sequence, error.value, roomData);
        gameServerConnection.sendMessage(response); //TODO: Should I use sendToClient!?
    }

    public void putSystemAvatarInRoom(final String roomName) {
        final long networkId = avatarIdToNetworkIdMap.get(systemAvatarId);
        enterRoom(networkId, 0, roomName);
    }

    public void removeSystemAvatarFromRoom(final ChatRoom room) {

    }

    public void enterRoom(final long networkId, final int sequence, final int roomId) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);
        final ChatRoom room = getChatRoomById(roomId);

        if (avatarId != null && room != null) {
            internalEnterRoom(networkId, room, sequence);
        } else {
            LOGGER.debug("Not entering room because it doesn't exist yet, and no pending enter is implemented.");
        }
    }

    public void enterRoom(final ChatAvatarId avatarId, final String roomName, final boolean forceCreate, final boolean createPrivate) {
        final String lowerRoomName = roomName.toLowerCase();

        final ChatRoom room = chatRoomMap.get(lowerRoomName);
        final long networkId = avatarIdToNetworkIdMap.get(avatarId);

        if (room != null && networkId != NetworkIdUtil.INVALID) {
            internalEnterRoom(networkId, room, 0);
        } else if (forceCreate) {
            createRoom(0, 0, lowerRoomName, false, !createPrivate, "");
            //We need to send them an enter when the room is created.
            LOGGER.debug("Created room, but not entering because pending enter is not implemented.");
        }
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

        ChatResult error = ChatResult.SUCCESS;
        int roomId = 0;

        final ChatRoom chatRoom = chatRoomMap.get(lowerRoomName);

        if (chatRoom != null) {
            roomId = chatRoom.getId();

            final int lastPartitionIndex = lowerRoomName.lastIndexOf('.');

            if (lastPartitionIndex != -1) {
                final String leaf = lowerRoomName.substring(0, lastPartitionIndex);

                //Don't allow the player to enter or leave the root system rooms.
                if (!leaf.equalsIgnoreCase(ChatRoomTypes.SYSTEM)) {
                    internalEnterRoom(networkId, chatRoom, sequence);
                    return;
                }
            }

            error = ChatResult.ROOM_UNKNOWN_FAILURE;
        } else {
            error = ChatResult.ADDRESS_NOT_ROOM;
        }

        //If we were unable to place the request, then we need to inform the client now.
        final ChatOnEnteredRoom fail = new ChatOnEnteredRoom(sequence, error, roomId, avatarId);
        sendToClient(networkId, fail);
    }

    private void internalEnterRoom(final long networkId, final ChatRoom chatRoom, int sequence) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);
        ChatResult error = ChatResult.SUCCESS;

        //Do some checks on if this avatar can enter the room.
        if (chatRoom.isInRoom(avatarId)) {
            error = ChatResult.ROOM_ALREADY_IN_ROOM;
        } else if (chatRoom.isBanned(avatarId)) {
            error = ChatResult.ROOM_BANNED_AVATAR;
        } else if (chatRoom.isPrivate() &&
                !chatRoom.isInvited(avatarId) &&
                !chatRoom.isModerator(avatarId) &&
                !chatRoom.isAdmin(avatarId) &&
                !chatRoom.isOwner(avatarId)) {
            error = ChatResult.ROOM_PRIVATE_ROOM;
        }

        if (error == ChatResult.SUCCESS) {
            //Send room data to player in case they didn't already know about the room...
            final ChatRoomList roomList = new ChatRoomList(Collections.singletonList(chatRoom.createChatRoomData()));
            sendToClient(networkId, roomList);
        }

        //Tell the client that made the request, the results of the request.
        final ChatOnEnteredRoom enteredRoom = new ChatOnEnteredRoom(sequence, error, chatRoom.getId(), avatarId);
        sendToClient(networkId, enteredRoom);

        if (error == ChatResult.SUCCESS) {
            //If success, tell everyone else in the room, if it's not a system room.
            if (!chatRoom.getName().contains(".system")) {
                final Iterator<ChatAvatarId> occupants = chatRoom.getAvatarsIterator();

                while (occupants.hasNext()) {
                    final ChatAvatarId occupantId = occupants.next();
                    final long occupantNetworkId = avatarIdToNetworkIdMap.get(occupantId);

                    if (occupantNetworkId != NetworkIdUtil.INVALID && occupantNetworkId != networkId) {
                        sendToClient(occupantNetworkId, enteredRoom);
                    }
                }
            }
        }
    }

    public void leaveRoom(final long networkId, final int sequence, final int roomId) {

    }

    public void destroyRoom(final long networkId, final int sequence, final int roomId) {

    }

    public void destroyRoom(final String roomName) {

    }

    public void removeAvatarFromRoom(final long requestorId, final ChatAvatarId avatarId, final String roomName) {

    }

    public void removeAvatarFromRoom(final ChatAvatarId avatarId, final String roomName) {

    }

    public void kickAvatarFromRoom(final long networkId, final ChatAvatarId avatarId, final String roomName) {

    }

    public void sendRoomMessage(final long networkId, final int sequence, final int roomId, final String message, final String outOfBand) {
        if (!message.isEmpty() || !outOfBand.isEmpty()) {
            LOGGER.debug("Sending message to room {}", roomId);

            final ChatAvatarId sender = networkIdToAvatarIdMap.get(networkId);
            final ChatRoom chatRoom = getChatRoomById(roomId);

            if (chatRoom != null && sender != null) {
                if (message.length() < MAX_ROOM_MESSAGE_LENGTH) {
                    //TODO: Spam limit
                    //TODO: Squelch time

                    final boolean squelched = false;
                    final boolean allowedToSpeak = false; //Are they allowed to speak?
                    final int timeUntilTalk = 0; //How long until they can talk again.

                    if (allowedToSpeak) {
                        internalSendRoomMessage(sender, chatRoom, message, outOfBand, sequence);
                    } else if (!squelched) {
                        final ChatSpamLimited chatSpamLimited = new ChatSpamLimited(timeUntilTalk);
                        sendToClient(networkId, chatSpamLimited);
                    }
                }
            }
        }
    }

    public void sendRoomMessage(final ChatAvatarId from, final String roomName, final String message, final String outOfBand) {

    }

    private void internalSendRoomMessage(final ChatAvatarId sender, final ChatRoom chatRoom, final String message, final String outOfBand, final int sequenceId) {
        ChatResult result = ChatResult.SUCCESS;

        final boolean privileged = chatRoom.isModerator(sender) || chatRoom.isOwner(sender) || chatRoom.isAdmin(sender);

        if (!chatRoom.isInRoom(sender))
            result = ChatResult.ROOM_SRC_NOT_IN_ROOM;

        else if (chatRoom.isModerated() && !privileged)
            result = ChatResult.ROOM_NO_PRIVILEGES;

        if (result == ChatResult.SUCCESS) {
            //Send the message to everyone in the room.
            final Iterator<ChatAvatarId> iterator = chatRoom.getAvatarsIterator();

            while (iterator.hasNext()) {
                final ChatAvatarId recipientId = iterator.next();
                notifyReceivedRoomMessage(sender, recipientId, chatRoom, message, outOfBand);
            }
        }

        final ChatOnSendRoomMessage chatOnSendRoomMessage = new ChatOnSendRoomMessage(sequenceId, result);
        sendToClient(sender, chatOnSendRoomMessage);
    }

    private void notifyReceivedRoomMessage(final ChatAvatarId senderId, final ChatAvatarId recipientId, final ChatRoom chatRoom, final String message, final String outOfBand) {
        //TODO: Check to prevent sending duplicate messages.

        if (senderId.equals(systemAvatarId)) {
            final ChatSystemMessage systemMessage = new ChatSystemMessage(ChatSystemMessage.Flags.BROADCAST, message, outOfBand);
            sendToClient(recipientId, systemMessage);
        } else {
            final ChatRoomMessage roomMessage = new ChatRoomMessage(chatRoom.getId(), senderId, message, outOfBand);
            sendToClient(recipientId, roomMessage);
        }

        //TODO: Log chat, but not planet-wide chat.
    }

    public void sendInstantMessage(final long fromId, final int sequence, final ChatAvatarId to, final String message, final String outOfBand) {

    }

    public void sendInstantMessage(final ChatAvatarId from, final ChatAvatarId to, final String message, final String outOfBand) {

    }

    public void addFriend(final long networkId, final int sequence, final ChatAvatarId friendId) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);

        ChatResult result = ChatResult.SUCCESS;

        //Make sure that the source avatar is known by the chat server.
        if (avatarId == null || networkId == NetworkIdUtil.INVALID)
            result = ChatResult.SRC_AVATAR_DOESNT_EXIST;

            //Check to make sure that the friend is known by the chat server.
        else if (friendId == null || avatarIdToNetworkIdMap.get(friendId) == NetworkIdUtil.INVALID)
            result = ChatResult.DST_AVATAR_DOESNT_EXIST;

            //Check if are already on the friends list.
        else if (friendRelationships.hasRelationshipWith(avatarId, friendId))
            result = ChatResult.DUPLICATE_FRIEND;

        //If no errors, then add the friend, and send the onAddFriend message.
        if (result == ChatResult.SUCCESS) {
            friendRelationships.add(avatarId, friendId);

            final ChatOnAddFriend msg = new ChatOnAddFriend(0, result.value);
            sendToClient(networkId, msg);

            //Receive notification if they are online, as long as you are not ignored by them.
            notifyFriendOfLogin(friendId, avatarId);
        }

        //Send the acknowledgement that the request was handled.
        final ChatOnChangeFriendStatus onChangeFriendStatus = new ChatOnChangeFriendStatus(0, networkId, friendId, true, result);
        sendToClient(networkId, onChangeFriendStatus);
    }

    public void removeFriend(final long networkId, final int sequence, final ChatAvatarId friendId) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);

        ChatResult result = ChatResult.SUCCESS;

        if (avatarId == null || networkId == NetworkIdUtil.INVALID)
            result = ChatResult.SRC_AVATAR_DOESNT_EXIST;

        else if (friendId == null || avatarIdToNetworkIdMap.get(friendId) == NetworkIdUtil.INVALID)
            result = ChatResult.DST_AVATAR_DOESNT_EXIST;

        else if (!friendRelationships.hasRelationshipWith(avatarId, friendId))
            result = ChatResult.FRIEND_NOT_FOUND;

        if (result == ChatResult.SUCCESS) {
            friendRelationships.remove(avatarId, friendId);

            //Don't need to notify yourself that they have gone offline if you are removing them.
        }

        //Send the acknowledgement that the request was handled.
        final ChatOnChangeFriendStatus onChangeFriendStatus = new ChatOnChangeFriendStatus(0, networkId, friendId, false, result);
        sendToClient(networkId, onChangeFriendStatus);
    }

    public void getFriendsList(final ChatAvatarId characterName) {
        LOGGER.debug("Getting friends list for {}", characterName.getFullName());

        final long networkId = avatarIdToNetworkIdMap.get(characterName);

        if (networkId != NetworkIdUtil.INVALID) {
            final Set<ChatAvatarId> friendList = friendRelationships.getRelationships(characterName);

            final ChatOnGetFriendsList msg = new ChatOnGetFriendsList(networkId, friendList);
            sendToClient(networkId, msg);
        }
    }

    public void addIgnore(final long networkId, final int sequence, final ChatAvatarId ignoreId) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);

        ChatResult result = ChatResult.SUCCESS;

        //Make sure that the source avatar is known by the chat server.
        if (avatarId == null || networkId == NetworkIdUtil.INVALID)
            result = ChatResult.SRC_AVATAR_DOESNT_EXIST;

            //Check to make sure that the ignore is known by the chat server.
        else if (ignoreId == null || avatarIdToNetworkIdMap.get(ignoreId) == NetworkIdUtil.INVALID)
            result = ChatResult.DST_AVATAR_DOESNT_EXIST;

            //Check if are already on the ignore list.
        else if (ignoreRelationships.hasRelationshipWith(avatarId, ignoreId))
            result = ChatResult.DUPLICATE_FRIEND;

        //If no errors, then add the ignore.
        if (result == ChatResult.SUCCESS) {
            //Notify them that you have logged out.
            notifyFriendOfLogout(avatarId, ignoreId); //Send this first, or it will get filtered out.

            ignoreRelationships.add(avatarId, ignoreId);
        }

        //Send the acknowledgement that the request was handled.
        final ChatOnChangeIgnoreStatus onChangeIgnoreStatus = new ChatOnChangeIgnoreStatus(0, networkId, ignoreId, true, result);
        sendToClient(networkId, onChangeIgnoreStatus);
    }

    public void removeIgnore(final long networkId, final int sequence, final ChatAvatarId ignoreId) {
        final ChatAvatarId avatarId = networkIdToAvatarIdMap.get(networkId);

        ChatResult result = ChatResult.SUCCESS;

        if (avatarId == null || networkId == NetworkIdUtil.INVALID)
            result = ChatResult.SRC_AVATAR_DOESNT_EXIST;

        else if (ignoreId == null || avatarIdToNetworkIdMap.get(ignoreId) == NetworkIdUtil.INVALID)
            result = ChatResult.DST_AVATAR_DOESNT_EXIST;

        else if (!ignoreRelationships.hasRelationshipWith(avatarId, ignoreId))
            result = ChatResult.FRIEND_NOT_FOUND;

        if (result == ChatResult.SUCCESS) {
            ignoreRelationships.remove(avatarId, ignoreId);

            //If they have you on their friends list, and you remove them from ignore, tell them that you are online.
            notifyFriendOfLogin(avatarId, ignoreId);
        }

        //Send the acknowledgement that the request was handled.
        final ChatOnChangeIgnoreStatus onChangeIgnoreStatus = new ChatOnChangeIgnoreStatus(0, networkId, ignoreId, false, result);
        sendToClient(networkId, onChangeIgnoreStatus);
    }

    public void getIgnoreList(final ChatAvatarId characterName) {
        LOGGER.debug("Getting ignore list for {}", characterName.getFullName());

        final long networkId = avatarIdToNetworkIdMap.get(characterName);

        if (networkId != NetworkIdUtil.INVALID) {
            final Set<ChatAvatarId> ignoreList = ignoreRelationships.getRelationships(characterName);

            final ChatOnGetFriendsList msg = new ChatOnGetFriendsList(networkId, ignoreList);
            sendToClient(networkId, msg);
        }
    }

    /**
     * Sends a status update message, indicating that the player has come online, to every player that connected to
     * the chat server and has the player in their friends list.
     *
     * @param avatarId The player that has come online.
     */
    private void notifyFriendsOfLogin(final ChatAvatarId avatarId) {
        final Set<ChatAvatarId> notificationList = friendRelationships.getReverseRelationships(avatarId);

        LOGGER.debug("Notifying {} players that {} has logged in.",
                notificationList.size(),
                avatarId.getFullName());

        notificationList.stream().forEach(friendId -> notifyFriendOfLogin(avatarId, friendId));
    }

    /**
     * Sends a status update message to an individual friend that the player has come online. The friend must be
     * connected to the chat server in order to receive the update.
     *
     * @param avatarId The player that has come online.
     * @param friendId The friend that will receive the status update.
     */
    private void notifyFriendOfLogin(final ChatAvatarId avatarId, final ChatAvatarId friendId) {
        final long friendNetworkId = avatarIdToNetworkIdMap.get(friendId);

        //Make sure they are online, not ignored, and a valid networkId
        if (friendNetworkId != NetworkIdUtil.INVALID
                && !ignoreRelationships.hasRelationshipWith(avatarId, friendId)
                && connectedPlayers.contains(friendNetworkId)) {
            final ChatFriendsListUpdate msg = new ChatFriendsListUpdate(avatarId, true);
            sendToClient(friendNetworkId, msg);
        }
    }

    /**
     * Sends a status update message, indicating that the player has gone offline, to every player that connected to
     * the chat server and has the player in their friends list.
     *
     * @param avatarId The player that has gone offline.
     */
    private void notifyFriendsOfLogout(final ChatAvatarId avatarId) {
        final Set<ChatAvatarId> notificationList = friendRelationships.getReverseRelationships(avatarId);

        LOGGER.debug("Notifying {} players that {} has logged out.",
                notificationList.size(),
                avatarId.getFullName());

        notificationList.stream().forEach(friendId -> notifyFriendOfLogout(avatarId, friendId));
    }

    /**
     * Sends a status update message to an individual friend that the player has gone offline. The friend must be
     * connected to the chat server in order to receive the update.
     *
     * @param avatarId The player that has gone offline.
     * @param friendId The friend that will receive the status update.
     */
    private void notifyFriendOfLogout(final ChatAvatarId avatarId, final ChatAvatarId friendId) {
        final long friendNetworkId = avatarIdToNetworkIdMap.get(friendId);

        //Make sure they are online, not ignored, and a valid networkId
        if (friendNetworkId != NetworkIdUtil.INVALID
                && !ignoreRelationships.hasRelationshipWith(avatarId, friendId)
                && connectedPlayers.contains(friendNetworkId)) {
            final ChatFriendsListUpdate msg = new ChatFriendsListUpdate(avatarId, false);
            sendToClient(friendNetworkId, msg);
        }
    }


    public void sendToClient(final ChatAvatarId avatarId, final GameNetworkMessage gnm) {
        final long networkId = avatarIdToNetworkIdMap.get(avatarId);

        if (networkId != NetworkIdUtil.INVALID)
            sendToClient(gameServerConnection, networkId, gnm);
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

    /**
     * Gets the chat room with the specified Id if it exists. Otherwise, returns null.
     *
     * @param roomId The id of the chat room.
     * @return The chat room with the id. Otherwise, null.
     */
    public ChatRoom getChatRoomById(final int roomId) {
        for (final ChatRoom room : chatRoomMap.values()) {
            if (room.getId() == roomId)
                return room;
        }

        return null;
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

    public ChatAvatarId makeChatAvatarId(final String characterName) {
        final String name = characterName.toLowerCase();
        final int spaceIndex = name.indexOf(' ');
        final String fixedName = spaceIndex != -1 ? name.substring(0, spaceIndex) : name;

        return new ChatAvatarId(
                systemAvatarId.getGameCode(),
                systemAvatarId.getCluster(),
                fixedName);
    }
}
