package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ChatAddFriend extends GameNetworkMessage {
    public ChatAddFriend(ChatAvatarId avatarId, String friendName) {
        super(0x04, 0x6FE7BD90);

        avatarId.writeToBuffer(this);
        writeAscii(friendName);
        writeInt(1); //unk

        //ChatAvatarId characterName;
        //int sequence
    }
}
