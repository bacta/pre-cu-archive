package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;

import java.nio.ByteBuffer;

public class ChatInstantMessageToClient extends GameNetworkMessage {

    //ChatAvatarId fromName
    //UnicodeString message
    //UnicodeString outOfBand

    public ChatInstantMessageToClient(ChatAvatarId sender, String message) {
        super(0x04, 0x3C565CED);

        sender.writeToBuffer(b);
        writeUnicode(message);
        writeInt(0); //StringIdParameter
    }

    @Override
    public ByteBuffer toBuffer() {
        return null;
    }
}
