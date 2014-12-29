package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;

public class GroupMissionCriticalObject implements SoeByteBufSerializable {

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeLong(0);
        message.writeLong(0);
    }

}
