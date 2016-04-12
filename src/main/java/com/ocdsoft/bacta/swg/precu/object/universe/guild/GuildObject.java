package com.ocdsoft.bacta.swg.precu.object.universe.guild;

import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaSet;
import com.ocdsoft.bacta.swg.precu.object.universe.UniverseObject;

public final class GuildObject extends UniverseObject {
    @Override
    public int getOpcode() {
        return 0x47494C44;
    } //'GILD'

    //GuildObjectMessage03
    private final AutoDeltaSet<String> abbrevs = new AutoDeltaSet<>(sharedPackage);
}
