package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ChatFriendsListUpdate extends GameNetworkMessage {
    public ChatFriendsListUpdate(ChatAvatarId avatarId, boolean online) {
        super(0x03, 0x6CD2FCD8);

        //ChatAvatarId characterName
        //bool isOnline

        avatarId.writeToBuffer(this);
        writeBoolean(online);
    }
}
