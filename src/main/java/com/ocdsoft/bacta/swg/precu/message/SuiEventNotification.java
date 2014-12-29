package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class SuiEventNotification extends SwgMessage {

    public SuiEventNotification() {
        super(0x4, 0x92d3564);
        
    }
    /**
         00 09 00 0B 04 00 64 35 2D 09 01 00 00 00 00 00
    00 00 01 00 00 00 01 00 00 00 01 00 00 00 30 00
    

     */
}
