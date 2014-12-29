package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class ClientInactivityMessage extends SwgMessage {

    public ClientInactivityMessage() {
        super(0x5325, 0x10f5d);
        
    }
    /**
         00 09 00 13 02 00 25 53 5D 0F 01 00 1A 24 

     */
}
