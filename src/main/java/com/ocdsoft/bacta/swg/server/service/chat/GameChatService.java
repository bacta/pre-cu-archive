package com.ocdsoft.bacta.swg.server.service.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.object.NetworkObject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.swg.server.game.GameServer;
import com.ocdsoft.bacta.swg.server.message.game.outofband.OutOfBandPackager;
import com.ocdsoft.bacta.swg.server.message.game.outofband.ProsePackage;
import com.ocdsoft.bacta.swg.server.message.game.outofband.ProsePackageParticipant;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.server.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.shared.chat.ChatRoomTypes;
import com.ocdsoft.bacta.swg.shared.chat.messages.*;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import gnu.trove.list.TLongList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by crush on 4/28/2016.
 * <p>
 * The GameChatService handles communications going from the GameServer to the ChatServer.
 * A {@link com.ocdsoft.bacta.soe.controller.GameNetworkMessageController} will receive a Chat message from the
 * SwgClient, interpret that request, and then send a new message to the ChatServer if it is valid.
 * <p>
 * This service also takes care of initializing the ChatServer for this GameServer, for example, registering all the
 * system chat rooms that this GameServer needs.
 */
@Singleton
public final class GameChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameChatService.class);

    private static final String SYSTEM_AVATAR_NAME = "SYSTEM";
    private static final AtomicInteger sequenceId = new AtomicInteger(1);

    private final ChatAvatarId serverAvatar;
    private final SoeUdpConnection chatServerConnection;

    private final String systemRoomPath;
    private final String imperialRoomPath;
    private final String rebelRoomPath;

    @Inject
    public GameChatService(final BactaConfiguration bactaConfiguration,
                           final OutgoingConnectionService outgoingConnectionService) {

        this.chatServerConnection = outgoingConnectionService.createOutgoingConnection(
                new InetSocketAddress(
                        bactaConfiguration.getStringWithDefault("Bacta/GameServer", "chatServerAddress", "127.0.0.1"),
                        bactaConfiguration.getIntWithDefault("Bacta/GameServer", "chatServerPort", 44491)),
                this::onConnectionEstablished);

        //Create the server chat avatar based off of configuration values.
        this.serverAvatar = new ChatAvatarId(
                bactaConfiguration.getStringWithDefault("ChatServer", "gameCode", "SWG"),
                bactaConfiguration.getString("ChatServer", "cluster"),
                SYSTEM_AVATAR_NAME);

        this.systemRoomPath = String.format("%s.%s.%s",
                serverAvatar.getGameCode(),
                serverAvatar.getCluster(),
                SYSTEM_AVATAR_NAME);

        this.imperialRoomPath = String.format(("%s.%s.%s"),
                serverAvatar.getGameCode(),
                serverAvatar.getCluster(),
                ChatRoomTypes.IMPERIAL);

        this.rebelRoomPath = String.format(("%s.%s.%s"),
                serverAvatar.getGameCode(),
                serverAvatar.getCluster(),
                ChatRoomTypes.REBEL);
    }

    private void onConnectionEstablished(final SoeUdpConnection chatServerConnection) {
        LOGGER.info("Connection to chat server was successful.");
    }

    public void emptyMail(final long sourceNetworkId, final long targetNetworkId) {
        if (targetNetworkId != NetworkObject.INVALID) {
            final ChatDeleteAllPersistentMessages message = new ChatDeleteAllPersistentMessages(sourceNetworkId, targetNetworkId);
            sendToChatServer(message);
        }
    }

    public void addFriend(final String player, final String friendName) {
        final ChatAvatarId playerId = constructChatAvatarId(player);
        final ChatAvatarId friendId = constructChatAvatarId(friendName);
        final ChatChangeFriendStatus message = new ChatChangeFriendStatus(0, playerId, friendId, true);
        sendToChatServer(message);
    }

    public void removeFriend(final String player, final String friendName) {
        final ChatAvatarId playerId = constructChatAvatarId(player);
        final ChatAvatarId friendId = constructChatAvatarId(friendName);
        final ChatChangeFriendStatus message = new ChatChangeFriendStatus(0, playerId, friendId, false);
        sendToChatServer(message);
    }

    public void getFriendList(final String player) {
        final ChatAvatarId avatarId = constructChatAvatarId(player);
        final ChatGetFriendsList message = new ChatGetFriendsList(avatarId);
        sendToChatServer(message);
    }

    public void addIgnore(final String player, final String ignoreName) {
        final ChatAvatarId playerId = constructChatAvatarId(player);
        final ChatAvatarId ignoreId = constructChatAvatarId(ignoreName);
        final ChatChangeIgnoreStatus message = new ChatChangeIgnoreStatus(0, playerId, ignoreId, true);
        sendToChatServer(message);
    }

    public void removeIgnore(final String player, final String ignoreName) {
        final ChatAvatarId playerId = constructChatAvatarId(player);
        final ChatAvatarId ignoreId = constructChatAvatarId(ignoreName);
        final ChatChangeIgnoreStatus message = new ChatChangeIgnoreStatus(0, playerId, ignoreId, false);
        sendToChatServer(message);
    }

    public void getIgnoreList(final String player) {
        final ChatAvatarId avatarId = constructChatAvatarId(player);
        final ChatGetIgnoreList message = new ChatGetIgnoreList(avatarId);
        sendToChatServer(message);
    }

    public void destroyRoom(final String roomPath) {
        final ChatDestroyRoomByName destroyRoom = new ChatDestroyRoomByName(roomPath);
        sendToChatServer(destroyRoom);
    }

    public void enterRoom(final String who, final String roomPath, final boolean forceCreate, final boolean createPrivate) {
        final ChatPutAvatarInRoom enter = new ChatPutAvatarInRoom(who, roomPath, forceCreate, createPrivate);
        sendToChatServer(enter);
    }

    public void exitRoom(final String who, final String roomPath) {
        final ChatAvatarId avatarId = new ChatAvatarId(serverAvatar.getGameCode(), serverAvatar.getCluster(), who);
        final ChatRemoveAvatarFromRoom removeAvatarMessage = new ChatRemoveAvatarFromRoom(avatarId, roomPath);
        sendToChatServer(removeAvatarMessage);
    }

    public void addModeratorToRoom(final String who, final String roomPath) {
        //Do nothing!?
        LOGGER.warn("addModeratorFromRoom(final String who, final String roomPath does nothing. Not sure why?");
    }

    public void removeModeratorFromRoom(final String who, final String roomPath) {
        //Do nothing!?
        LOGGER.warn("removeModeratorFromRoom(final String who, final String roomPath does nothing. Not sure why?");
    }

    /**
     * Sends a system-specific message to a player.
     *
     * @param to        The name of the player whom will receive the message.
     * @param message   The message to be sent.
     * @param outOfBand The packed {@link ProsePackage} to send with the message.
     */
    public void sendSystemMessage(final String to, final StringId message, final String outOfBand) {
        sendSystemMessage(to, "@" + message.getCanonicalRepresentation(), outOfBand);
    }

    /**
     * Sends a system-specific message to a player.
     *
     * @param to        The name of the player whom will receive the message.
     * @param message   The message to be sent.
     * @param outOfBand The packed {@link ProsePackage} to send with the message.
     */
    public void sendSystemMessage(final String to, final String message, final String outOfBand) {
        final int spacePos = to.indexOf(' ');

        if (spacePos != -1)
            sendInstantMessage(serverAvatar.getFullName(), to.substring(0, spacePos), message, outOfBand);
        else
            sendInstantMessage(serverAvatar.getFullName(), to, message, outOfBand);
    }

    public void sendSystemMessage(final String to, final ProsePackage prosePackage) {
        final String outOfBand = OutOfBandPackager.pack(prosePackage, -1);
        sendSystemMessage(to, "", outOfBand);
    }

    /**
     * Sends a system-specific message to a player.
     *
     * @param to        The player whom will receive the message.
     * @param message   The message to be sent.
     * @param outOfBand The packed {@link ProsePackage} to send with the message.
     */
    public void sendSystemMessage(final ServerObject to, final StringId message, final String outOfBand) {
        final ChatSystemMessage systemMessage = new ChatSystemMessage(
                ChatSystemMessage.Flags.PERSONAL,
                "@" + message.getCanonicalRepresentation(),
                outOfBand);

        final SoeUdpConnection client = to.getConnection();

        if (client != null) {
            client.sendMessage(systemMessage);
        } else {
            sendSystemMessage(to.getAssignedObjectFirstName(), message, outOfBand);
        }
    }

    /**
     * Sends a system-specific message to a player.
     *
     * @param to        The player whom will receive the message.
     * @param message   The message to be sent.
     * @param outOfBand The packed {@link ProsePackage} to send with the message.
     */
    public void sendSystemMessage(final ServerObject to, final String message, final String outOfBand) {
        final ChatSystemMessage systemMessage = new ChatSystemMessage(ChatSystemMessage.Flags.PERSONAL, message, outOfBand);

        final SoeUdpConnection client = to.getConnection();

        if (client != null) {
            client.sendMessage(systemMessage);
        } else {
            sendSystemMessage(to.getAssignedObjectFirstName(), message, outOfBand);
        }
    }

    /**
     * Sends a system-specific message to a player.
     *
     * @param to           The player whom will receive the message.
     * @param prosePackage The prose package that contains the message.
     */
    public void sendSystemMessage(final ServerObject to, final ProsePackage prosePackage) {
        final String outOfBand = OutOfBandPackager.pack(prosePackage, -1);
        sendSystemMessage(to, "", outOfBand);
    }

    public void sendSystemMessageSimple(final ServerObject to, final StringId stringId, final ServerObject target) {

        final ProsePackageParticipant actorParticipant = new ProsePackageParticipant(to.getNetworkId());
        final ProsePackageParticipant targetParticipant;

        if (target != null) {
            final String assignedObjectName = target.getAssignedObjectName();

            if (!assignedObjectName.isEmpty()) {
                targetParticipant = new ProsePackageParticipant(target.getNetworkId(), assignedObjectName);
            } else {
                targetParticipant = new ProsePackageParticipant(target.getNetworkId(), target.getNameStringId());
            }
        } else {
            targetParticipant = ProsePackageParticipant.EMPTY;
        }

        sendSystemMessage(to, new ProsePackage(stringId, actorParticipant, targetParticipant));
    }

    public void sendInstantMessage(final String from, final String to, final String message, final String outOfBand) {
        final ChatInstantMessageFromGame instantMessage = new ChatInstantMessageFromGame(to, from, message, outOfBand);
        sendToChatServer(instantMessage);
    }

    public void sendPersistentMessage(final String from, final String to, final String subject, final String message, final String outOfBand) {
        final ChatPersistentMessageFromGame persistentMessage = new ChatPersistentMessageFromGame(to, from, subject, message, outOfBand);
        sendToChatServer(persistentMessage);
    }

    public void sendPersistentMessage(final ServerObject from, final String to, final StringId subject, final String message, final String outOfBand) {
        final String subjectString = "@" + subject.getCanonicalRepresentation();

        sendPersistentMessage(from.getEncodedObjectName(), to, subjectString, message, outOfBand);
    }

    public void sendPersistentMessage(final ServerObject from, final String to, final StringId subject, final StringId message, final String outOfBand) {
        final String subjectString = "@" + subject.getCanonicalRepresentation();
        final String messageString = "@" + message.getCanonicalRepresentation();

        sendPersistentMessage(from.getEncodedObjectName(), to, subjectString, messageString, outOfBand);
    }

    public void sendToRoom(final String from, final String roomName, final String message, final String outOfBand) {
        final ChatRoomMessageFromGame msg = new ChatRoomMessageFromGame(from, roomName, message, outOfBand);
        sendToChatServer(msg);
    }

    public void broadcastSystemMessageToCluster(final String message, final String outOfBand) {
        sendToRoom(SYSTEM_AVATAR_NAME, systemRoomPath, message, outOfBand);
    }

    public ChatAvatarId constructChatAvatarId(final ServerObject to) {
        return constructChatAvatarId(to.getAssignedObjectFirstName());
    }

    public ChatAvatarId constructChatAvatarId(final String to) {
        return new ChatAvatarId(serverAvatar.getGameCode(), serverAvatar.getCluster(), to);
    }

    /**
     * Invite a player to a chat room.
     *
     * @param to   The player to invite.
     * @param room The room to which to invite the player.
     */
    public void invite(final String to, final String room) {
        final ChatAvatarId avatarId = constructChatAvatarId(to);
        final ChatInviteAvatarToRoom inviteMessage = new ChatInviteAvatarToRoom(avatarId, room);
        sendToChatServer(inviteMessage);
    }

    /**
     * Uninvite a player to a chat room.
     *
     * @param to   The player to uninvite.
     * @param room The room to which  to uninvite the player.
     */
    public void uninvite(final String to, final String room) {
        final ChatAvatarId avatarId = constructChatAvatarId(to);
        final ChatUninviteFromRoom uninviteMessage = new ChatUninviteFromRoom(0, avatarId, room);
        sendToChatServer(uninviteMessage);
    }

    public void inviteGroupMembers(final long inviter, final ChatAvatarId groupLeader, final String room, final TLongList members) {
        final ChatInviteGroupMembersToRoom inviteMessage = new ChatInviteGroupMembersToRoom(inviter, groupLeader, room, members.toArray());
        sendToChatServer(inviteMessage);
    }

    public void createSystemRooms(final String galaxyName, final String planetName) {
        final String avatarName = serverAvatar.getFullName();
        final String gamePrefix = serverAvatar.getGameCode() + ".";

        //TODO: Should these be universe wide chat rooms be created at the game server level? Probably not.
        createRoom(avatarName, true, gamePrefix + ChatRoomTypes.UNIVERSE, "public chat for all galaxies, cannot create rooms here");
        createRoom(avatarName, true, gamePrefix + ChatRoomTypes.SYSTEM, "system messages for all galaxies");
        createRoom(avatarName, true, gamePrefix + ChatRoomTypes.CHAT, "public chat for all galaxies, can create rooms here");

        final String galaxyRoom = gamePrefix + galaxyName + ".";

        //TODO: Should we move the system chat rooms to a configuration file instead?

        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.GALAXY, "public chat for the whole galaxy, cannot create rooms here");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.SYSTEM, "system messages for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.CHAT, "public chat for this galaxy, can create rooms here");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.IMPERIAL, "Imperial chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.IMPERIAL_WAR_ROOM, "Imperial war room chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.REBEL, "Rebel chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.REBEL_WAR_ROOM, "Rebel war room chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.BOUNTY_HUNTER, "Bounty Hunter chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.COMMANDO, "Commando chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.ENTERTAINER, "Entertainer chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.FORCE_SENSITIVE, "Force Sensitive chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.MEDIC, "Medic chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.OFFICER, "Officer chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.PILOT, "Pilot chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.POLITICIAN, "Politician chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.SMUGGLER, "Smuggler chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.SPY, "Spy chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.TRADER, "Trader chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.BEAST_MASTERY, "Beast Mastery chat for this galaxy");
        createRoom(avatarName, true, galaxyRoom + ChatRoomTypes.WARDEN, "Warden chat for this galaxy");

        final String planetRoom = galaxyRoom + planetName + ".";
        createRoom(avatarName, true, planetRoom + ChatRoomTypes.PLANET, "public chat for this planet, cannot create rooms here");
        createRoom(avatarName, true, planetRoom + ChatRoomTypes.SYSTEM, "system messages for this planet, cannot create rooms here");
        createRoom(avatarName, true, planetRoom + ChatRoomTypes.CHAT, "public chat for this planet, can create rooms here");

        //Tell GroupService to create all the group chat rooms.
        //Tell GuildService to create all the guild chat rooms.
        //Tell CityService to create all the city chat rooms.
    }

    public void createRoom(final String ownerName, final boolean isPublic, final String roomPath, final String roomTitle) {
        if (!SYSTEM_AVATAR_NAME.equalsIgnoreCase(ownerName)) {
            if (!isAppropriateText(roomPath)) {
                sendSystemMessage(ownerName, new StringId("ui_chatroom", "create_err_profane_name"), "");
                return;
            }

            if (!isAppropriateText(roomTitle)) {
                sendSystemMessage(ownerName, new StringId("ui_chatroom", "create_err_profane_title"), "");
                return;
            }
        }

        final ChatCreateRoom create = new ChatCreateRoom(sequenceId.getAndIncrement(), ownerName, roomPath, false, isPublic, roomTitle);
        sendToChatServer(create);
    }

    public ChatError isAllowedToEnterRoom(final CreatureObject who, final String room) {
        if (who.getConnection() != null && who.getConnection().isGod())
            return ChatError.SUCCESS;

        //Player must be of correct faction to enter factional chat rooms.
        //TODO: Faction check.

        //Player must be a mayor and guild leader of the same faction of city and guild aligned with the same faction
        //to enter war room chat rooms.

        //Player must be a warden to enter the warden room.

        return ChatError.SUCCESS;
    }

    public void sendToChatServer(final GameNetworkMessage message) {
        if (chatServerConnection != null) {
            chatServerConnection.sendMessage(message);
        } else {
            LOGGER.error("Tried to send message of type {} but no chat server connection existed.",
                    message.getClass().getSimpleName());
        }
    }

    private boolean isAppropriateText(final String text) {
        LOGGER.warn("Not implemented");
        return true;
    }
}
