package com.ocdsoft.bacta.swg.server.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.GameClientMessage;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.serialize.GameNetworkMessageSerializer;
import com.ocdsoft.bacta.swg.server.chat.message.ChatAvatarConnected;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatAvatarId;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatOnConnectAvatar;
import com.ocdsoft.bacta.swg.shared.foundation.NetworkIdUtil;
import gnu.trove.TCollections;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by crush on 5/23/2016.
 */
@Singleton
public class SwgChatServer implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwgChatServer.class);

    private final GameNetworkMessageSerializer serializer;

    private final ChatAvatarId systemAvatarId;
    private final TObjectLongMap<ChatAvatarId> avatarIdToNetworkIdMap;
    private final TLongObjectMap<ChatAvatarId> networkIdToAvatarIdMap;

    private final TLongSet connectedPlayers;

    private SoeUdpConnection gameServerConnection;

    @Inject
    public SwgChatServer(final ChatServerConfiguration configuration,
                         final GameNetworkMessageSerializer serializer) {

        this.serializer = serializer;
        this.networkIdToAvatarIdMap = TCollections.synchronizedMap(new TLongObjectHashMap<>());
        this.avatarIdToNetworkIdMap = TCollections.synchronizedMap(new TObjectLongHashMap<>());
        this.connectedPlayers = new TLongHashSet();

        this.systemAvatarId = new ChatAvatarId(
                configuration.getGameCode(),
                configuration.getClusterName(),
                configuration.getSystemAvatarName());
    }

    public void notifyGameServerOnline(final SoeUdpConnection connection) {
        this.gameServerConnection = connection;
    }

    /**
     * Gets a ChatAvatarId based on their NetworkId. This pulls from a persistent data store, so even if the player
     * is not connected, we should be able to retrieve the ChatAvatarId belonging to the given NetworkId.
     *
     * @param networkId The NetworkId that owns the ChatAvatarId.
     * @return The ChatAvatarId belonging to the NetworkId, or null if no ChatAvatarId could be found.
     */
    public ChatAvatarId getAvatarIdByNetworkId(final long networkId) {
        if (networkId == NetworkIdUtil.INVALID)
            return null;

        return networkIdToAvatarIdMap.get(networkId);
    }

    /**
     * Gets the NetworkId belonging to a given ChatAvatarId.
     *
     * @param avatarId The ChatAvatarId for which to get the NetworkId.
     * @return The NetworkId assigned to the ChatAvatarId. If it doesn't exist, then {@link NetworkIdUtil#INVALID}
     * is returned instead.
     */
    public long getNetworkIdByAvatarId(final ChatAvatarId avatarId) {
        if (avatarIdToNetworkIdMap.containsKey(avatarId))
            return avatarIdToNetworkIdMap.get(avatarId);

        return NetworkIdUtil.INVALID;
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
                connection.getRemoteAddress().getHostName(),
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
     * Looks up the connection associated with a networkId, and sends the message there. If no connection exists, then
     * the message will be placed into a pending queue until a connection does exist.
     *
     * @param networkId The NetworkId of the player to whom to send the message.
     * @param gnm       The message to send.
     */
    public void sendToClient(final long networkId, final GameNetworkMessage gnm) {
        final ByteBuffer messageBuffer = serializer.writeToBuffer(gnm);

        final GameClientMessage gameClientMessage = new GameClientMessage(
                new long[]{networkId}, true, messageBuffer);

        if (gameServerConnection != null)
            gameServerConnection.sendMessage(gameClientMessage);
        else {
            //TODO: Implement pending queue.
            LOGGER.warn("Tried to send message of type {} to client, but no valid connection was found.",
                    gnm.getClass().getSimpleName());
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {

    }

    private ChatAvatarId makeChatAvatarId(final String characterName) {
        assert characterName.indexOf(' ') == -1 : "A character name was passed to chat server that wasn't just a first name. This shouldn't happen.";

        return new ChatAvatarId(
                systemAvatarId.getGameCode(),
                systemAvatarId.getCluster(),
                characterName.toLowerCase()); //Make sure the name is lowercase.
    }
}
