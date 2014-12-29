package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatFriendsListUpdate extends SwgMessage {
    public ChatFriendsListUpdate(ChatAvatarId avatarId, boolean online) {
        super(0x03, 0x6CD2FCD8);

        //ChatAvatarId characterName
        //bool isOnline

        avatarId.writeToBuffer(this);
        writeBoolean(online);
    }
}
