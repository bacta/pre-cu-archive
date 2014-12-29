package com.ocdsoft.bacta.swg.precu.chat;

import com.ocdsoft.bacta.swg.network.soe.client.SoeUdpClient;
import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatInstantMessageToClient;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnAddFriend;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnConnectAvatar;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnSendInstantMessage;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 7/24/2014.
 */
public class ChatServerAgentHandler {
    private final SoeUdpClient client;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public ChatServerAgentHandler(SoeUdpClient client) {
        this.client = client;
    }

    public void onConnectAvatar(final ChatAvatarId avatarId) {
        client.sendMessage(new ChatOnConnectAvatar());
    }

    public void onInstantMessageReceived(final ChatAvatarId senderId, final String message) {
        client.sendMessage(new ChatInstantMessageToClient(senderId, message));
    }

    public void onInstantMessageSent(final ChatAvatarId recipientId, final String message, final int sequenceId, final int errorCode) {
        //0 success.
        //4 crush is not online.
        //9 You sent a tell but crush is ignoring messages from you.
        //16 Your message to crush was not sent because it was too long.
        //Anything else is just a generic error message.

        client.sendMessage(new ChatOnSendInstantMessage(sequenceId, errorCode));
    }

    public void onFriendAdded(final ChatAvatarId friendId) {
        logger.info("onFriendAdded");
        client.sendMessage(new ChatOnAddFriend());
    }

    public void onFriendStatusChanged(final ChatAvatarId friendId, boolean status) {
        logger.info("onFriendStatusChanged");
        //client.sendMessage(new ChatOnChangeFriendStatus());
    }
}
