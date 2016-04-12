package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class SuiEventNotification extends GameNetworkMessage {

    public SuiEventNotification() {
        super(0x4, 0x92d3564);
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    /**
         00 09 00 0B 04 00 64 35 2D 09 01 00 00 00 00 00
    00 00 01 00 00 00 01 00 00 00 01 00 00 00 30 00
    

     */
}
