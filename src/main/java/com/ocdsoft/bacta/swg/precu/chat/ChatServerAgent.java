package com.ocdsoft.bacta.swg.precu.chat;


import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import lombok.Getter;

import java.util.List;

/**
 * A ChatServerAgent communicates directly with a ChatServer on behalf of a chat user.
 */
public abstract class ChatServerAgent {

    @Getter
    private final ChatServerAgentHandler handler;

    public ChatServerAgent(ChatServerAgentHandler handler) {
        this.handler = handler;
    }

    /**
     * Connects to the underlying chat server connection, and registers connection listeners that will attempt
     * to maintain the chat server connection.
     */
    public abstract void connect();

    /**
     * Disconnects from the underlying chat server connection.
     */
    public abstract void disconnect();

    /**
     * Attempts to login to the connected chat server connection. The agent should already have a connection and
     * {@link ChatAvatarId} at this point.
     *
     * @param password The password that will be used for authentication.
     */
    public abstract void login(final String password);

    /**
     * Gets the {@link ChatAvatarId} for the chat user for this agent.
     *
     * @return ChatAvatarId
     */
    public abstract ChatAvatarId getAvatarId();

    /**
     * Adds a new friend to the user's friend list.
     *
     * @param friendId The {@link ChatAvatarId} of the friend to be added to the friends list.
     */
    public abstract void addFriend(final ChatAvatarId friendId);

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friendId The {@link ChatAvatarId} of the friend to be removed from the friends list.
     */
    public abstract void removeFriend(final ChatAvatarId friendId);

    /**
     * Retrieves a list of all the user's friends from the connected chat server.
     *
     * @return List of all of this user's friends.
     */
    public abstract List<ChatAvatarId> retrieveFriends();

    /**
     * Sends a persistent message to a chat user with the given subject and body.
     *
     * @param recipientId The {@link ChatAvatarId} of the user who should receive the message.
     * @param subject     The subject of the message.
     * @param body        The body of the message.
     */
    public abstract void sendPersistentMessage(final ChatAvatarId recipientId, final String subject, final String body);

    /**
     * Sends an instant message to a chat user with the given body.
     *
     * @param recipientId The {@link ChatAvatarId} of the user who should receive the message.
     * @param body        The body of the message.
     */
    public abstract void sendInstantMessage(final ChatAvatarId recipientId, final String body, final int sequenceId);

    public abstract void sendRoomMessage(final String roomId, final String body);
}
