package com.ocdsoft.bacta.swg.precu.message.ship;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

/**
 * Created by crush on 8/16/2014.
 */
public class ShipUpdateTransformMessage extends GameNetworkMessage {
    public ShipUpdateTransformMessage(int priority, int opname) {
        super(priority, opname);

        //short shipId
        //PackedTransform transform
        //PackedVelocity velocity
        //PackedRotationRate yawRate
        //PackedRotationRate pitchRate
        //PackedRotationRate rollRate
        //int syncStampLong
    }
}
