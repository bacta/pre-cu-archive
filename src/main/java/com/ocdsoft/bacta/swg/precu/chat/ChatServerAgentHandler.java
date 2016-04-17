package com.ocdsoft.bacta.swg.precu.chat;

import com.ocdsoft.bacta.soe.chat.message.ChatInstantMessageToClient;
import com.ocdsoft.bacta.soe.chat.message.ChatOnAddFriend;
import com.ocdsoft.bacta.soe.chat.message.ChatOnConnectAvatar;
import com.ocdsoft.bacta.soe.chat.message.ChatOnSendInstantMessage;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;

import org.slf4j.LoggerFactory;

/**
 * Created by crush on 7/24/2014.
 */
public class ChatServerAgentHandler {
    private final SoeUdpConnection connection;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public ChatServerAgentHandler(SoeUdpConnection connection) {
        this.connection = connection;
    }

    public void onConnectAvatar(final ChatAvatarId avatarId) {
        connection.sendMessage(new ChatOnConnectAvatar());
    }

    public void onInstantMessageReceived(final ChatAvatarId senderId, final String message) {
        connection.sendMessage(new ChatInstantMessageToClient(senderId, message));
    }

    public void onInstantMessageSent(final ChatAvatarId recipientId, final String message, final int sequenceId, final int errorCode) {
        //0 success.
        //4 crush is not online.
        //9 You sent a tell but crush is ignoring messages from you.
        //16 Your message to crush was not sent because it was too long.
        //Anything else is just a generic error message.

        connection.sendMessage(new ChatOnSendInstantMessage(sequenceId, errorCode));
    }

    public void onFriendAdded(final ChatAvatarId friendId) {
        logger.info("onFriendAdded");
        connection.sendMessage(new ChatOnAddFriend());
    }

    public void onFriendStatusChanged(final ChatAvatarId friendId, boolean status) {
        logger.info("onFriendStatusChanged");
        //connection.sendMessage(new ChatOnChangeFriendStatus());
    }
}
