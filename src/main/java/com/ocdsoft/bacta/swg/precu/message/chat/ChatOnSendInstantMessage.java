package com.ocdsoft.bacta.swg.precu.message.chat;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ChatOnSendInstantMessage extends SwgMessage {

    //int result
    //int sequence

    public ChatOnSendInstantMessage(int sequence, int errorCode) {
        super(0x03, 0x88DBB381);

        writeInt(errorCode);
        writeInt(sequence);
    }
}
