package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class IsVendorOwnerMessage extends GameNetworkMessage {

    public IsVendorOwnerMessage() {
        super(0x5a3b, 0xa0fa21b5);
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    /**
         00 09 00 0C 02 00 3B 5A B5 21 FA A0 40 00 00 00
    00 00 00 07 D9 

     */
}
