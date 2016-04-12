package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;

import java.nio.ByteBuffer;

public class GroupMissionCriticalObject implements ByteBufferSerializable {

    private long unknown1;
    private long unknown2;

    public GroupMissionCriticalObject() {
        unknown1 = 0;
        unknown2 = 0;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        unknown1 = buffer.getLong();
        unknown2 = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(unknown1);
        buffer.putLong(unknown2);
    }
}
