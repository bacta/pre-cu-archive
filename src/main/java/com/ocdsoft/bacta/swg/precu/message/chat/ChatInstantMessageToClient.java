package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatInstantMessageToClient extends SwgMessage {

    //ChatAvatarId fromName
    //UnicodeString message
    //UnicodeString outOfBand

    public ChatInstantMessageToClient(ChatAvatarId sender, String message) {
        super(0x04, 0x3C565CED);

        sender.writeToBuffer(this);
        writeUnicode(message);
        writeInt(0); //StringIdParameter
    }
}
