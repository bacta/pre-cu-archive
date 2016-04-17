package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class ClientInactivityMessage extends GameNetworkMessage {

    public ClientInactivityMessage() {
        super(0x5325, 0x10f5d);
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    /**
         00 09 00 13 02 00 25 53 5D 0F 01 00 1A 24 

     */
}
