package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class PlayerMoneyRequest extends GameNetworkMessage {

    public PlayerMoneyRequest() {
        super(0x1, 0x9d105aa1);
        
    }
    /**
         01 00 A1 5A 10 9D 

     */
}
