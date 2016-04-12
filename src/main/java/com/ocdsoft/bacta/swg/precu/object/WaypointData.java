package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.precu.message.outofband.WaypointDataBase;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/13/2014.
 */
public class WaypointData extends WaypointDataBase {
    private long networkId;

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        super.readFromBuffer(buffer);
        this.networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        super.writeToBuffer(buffer);
        buffer.putLong(networkId);
    }
}
