package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class IsVendorOwnerMessage extends SwgMessage {

    public IsVendorOwnerMessage() {
        super(0x5a3b, 0xa0fa21b5);
        
    }
    /**
         00 09 00 0C 02 00 3B 5A B5 21 FA A0 40 00 00 00
    00 00 00 07 D9 

     */
}
