package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
public class SkillModEntry implements ByteBufferSerializable {

    private int modifier;
    private int bonus;

    public SkillModEntry(final int modifier, final int bonus) {
        this.modifier = modifier;
        this.bonus = bonus;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        modifier = buffer.getInt();
        bonus = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(modifier);
        buffer.putInt(bonus);
    }
}
