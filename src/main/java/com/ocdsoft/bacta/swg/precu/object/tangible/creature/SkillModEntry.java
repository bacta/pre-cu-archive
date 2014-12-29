package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import lombok.Getter;

public class SkillModEntry implements SoeByteBufSerializable {

    @Getter
    private int modifier = 0;
    @Getter
    private int bonus = 0;

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeInt(modifier);
        message.writeInt(bonus);
    }

}
