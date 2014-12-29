package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.message.outofband.WaypointDataBase;

/**
 * Created by crush on 8/13/2014.
 */
public class WaypointData extends WaypointDataBase {
    private long networkId;

    public WaypointData(SoeByteBuf buffer) {
        super(buffer);
    }
}
