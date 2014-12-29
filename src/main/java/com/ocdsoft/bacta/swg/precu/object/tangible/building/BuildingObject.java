package com.ocdsoft.bacta.swg.precu.object.tangible.building;

import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;

public final class BuildingObject extends TangibleObject {
    @Override
    public int getOpcode() {
        return 0x4255494F;
    } //'BUIO'
}
