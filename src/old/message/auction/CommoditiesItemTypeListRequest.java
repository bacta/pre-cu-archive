package com.ocdsoft.bacta.swg.precu.message.auction;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class CommoditiesItemTypeListRequest extends GameNetworkMessage {
 
    public CommoditiesItemTypeListRequest() {
        super(0x2, 0x48f493c5);
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    /**
         02 00 C5 93 F4 48 00 00 
     */
}
