package com.ocdsoft.bacta.swg.precu.object.intangible;

import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.archive.delta.AutoDeltaInt;

public abstract class IntangibleObject extends SceneObject {
    @Override
    public int getOpcode() {
        return 0x49544E4F;
    } //'ITNO'

    // IntangibleObject03
    private final AutoDeltaInt count = new AutoDeltaInt(0, sharedPackage);
}
