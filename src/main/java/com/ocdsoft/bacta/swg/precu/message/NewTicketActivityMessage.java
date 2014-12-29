package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class NewTicketActivityMessage extends SwgMessage {

    public NewTicketActivityMessage() {
        super(0x2, 0x274f4e78);
        
    }
    /**
         02 00 78 4E 4F 27 00 00 00 00 

     */
}
