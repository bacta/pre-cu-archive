package com.ocdsoft.bacta.swg.precu.message.ship;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

/**
 * Created by crush on 8/16/2014.
 */
public class ShipUpdateTransformCollisionMessage extends SwgMessage {
    public ShipUpdateTransformCollisionMessage(int priority, int opname) {
        super(priority, opname);

        //NetworkId networkId
        //Transform transform
        //Vector velocity
        //int syncStampLong
    }
}
