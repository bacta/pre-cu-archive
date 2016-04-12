package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class ChatInstantMessageToCharacter extends GameNetworkMessage {

    public ChatInstantMessageToCharacter() {
        super(0x5, 0x84bb21f7);
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    /**
         00 0A 05 00 F7 21 BB 84 03 00 53 57 47 08 00 42    6C 6F 6F 64 66 69 6E 04 00 6B 79 6C 65 05 00 00    00 68 00 65 00 6C 00 6C 00 6F 00 00 00 00 00 02    00 00 00 00 5A F5 
     */
}
