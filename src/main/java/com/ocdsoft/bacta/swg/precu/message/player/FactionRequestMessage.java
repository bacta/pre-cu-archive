package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class FactionRequestMessage extends GameNetworkMessage {

    public FactionRequestMessage() {
        super(0x1, 0xc1b03b81);
        
    }
    /**
         01 00 81 3B B0 C1 

     */
}
