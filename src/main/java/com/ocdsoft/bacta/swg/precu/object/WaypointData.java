package com.ocdsoft.bacta.swg.precu.object;


import com.ocdsoft.bacta.swg.precu.message.game.outofband.WaypointDataBase;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/13/2014.
 */
public final class WaypointData extends WaypointDataBase {
    private final long networkId;

    public WaypointData(final ByteBuffer buffer) {
        super(buffer);
        this.networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        super.writeToBuffer(buffer);
        buffer.putLong(networkId);
    }
}
