package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class StomachRequestMessage extends SwgMessage {

    public StomachRequestMessage() {
        super(0x1, 0xb75dd5d7);

    }
    /**
     01 00 D7 D5 5D B7

     */
}
