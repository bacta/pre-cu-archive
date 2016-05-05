package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public final class ShipFormationGroupMember implements ByteBufferWritable {
    private final long memberId;
    private final int memberIndex; //?

    public ShipFormationGroupMember(final ByteBuffer buffer) {
        memberId = buffer.getLong();
        memberIndex = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(memberId);
        buffer.putInt(memberIndex);
    }
}
