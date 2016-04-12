package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import lombok.Getter;

import java.nio.ByteBuffer;

public class ShipFormationGroupMember implements ByteBufferSerializable {
    @Getter private long memberId;
    @Getter private int memberIndex; //?

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        memberId = buffer.getLong();
        memberIndex = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(memberId);
        buffer.putInt(memberIndex);
    }
}
