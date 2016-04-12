package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class NewTicketActivityMessage extends GameNetworkMessage {

    public NewTicketActivityMessage() {
        super(0x2, 0x274f4e78);
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    /**
         02 00 78 4E 4F 27 00 00 00 00 

     */
}
