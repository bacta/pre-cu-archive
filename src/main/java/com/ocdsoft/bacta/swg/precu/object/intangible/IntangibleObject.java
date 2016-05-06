package com.ocdsoft.bacta.swg.precu.object.intangible;

import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;

public abstract class IntangibleObject extends ServerObject {
    @Override
    public int getObjectType() {
        return 0x49544E4F;
    } //'ITNO'

    // IntangibleObject03
    private final AutoDeltaInt count = new AutoDeltaInt(0, sharedPackage);
}
