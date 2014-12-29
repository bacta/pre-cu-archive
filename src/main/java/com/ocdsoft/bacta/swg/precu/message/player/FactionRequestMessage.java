package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class FactionRequestMessage extends SwgMessage {

    public FactionRequestMessage() {
        super(0x1, 0xc1b03b81);
        
    }
    /**
         01 00 81 3B B0 C1 

     */
}
