package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class GetMapLocationsMessage extends SwgMessage {
 
    public GetMapLocationsMessage() {
        super(0x5, 0x1a7ab839);
        
    }
    /**
         00 13 05 00 39 B8 7A 1A 08 00 74 61 74 6F 6F 69
    6E 65 00 00 00 00 00 00 00 00 00 00 00 00 

     */
}
