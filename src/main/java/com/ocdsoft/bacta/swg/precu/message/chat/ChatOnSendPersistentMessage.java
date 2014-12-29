package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatOnSendPersistentMessage extends SwgMessage {

    //int result
    //int sequence

    public ChatOnSendPersistentMessage(int errorCode, int sequence) {
        super(0x03, 0x94E7A7AE);

        writeInt(errorCode);
        writeInt(sequence);
    }
}
