package com.ocdsoft.bacta.swg.precu.message.auction;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/15/2014.
 */
public class CommoditiesItemTypeListResponse extends GameNetworkMessage {
    public CommoditiesItemTypeListResponse() {
        super(0x07, 0xD4E937FC);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
