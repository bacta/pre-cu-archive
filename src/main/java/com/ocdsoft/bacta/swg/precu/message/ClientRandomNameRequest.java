package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

public class ClientRandomNameRequest extends GameNetworkMessage {

    public ClientRandomNameRequest() {
        super(0xffffb6d1, 0x25d6d1);
        
    }
    /**
         00 09 00 03 02 00 D1 B6 D1 D6 25 00 6F 62 6A 65
    63 74 2F 63 72 65 61 74 75 72 65 2F 70 6C 61 79
    65 72 2F 68 75 6D 61 6E 5F 6D 61 6C 65 2E 69 66
    66 00 AA 8C 

     */
}
