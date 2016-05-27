package com.ocdsoft.bacta.swg.server.game.object.universe.group;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public class GroupMissionCriticalObject implements ByteBufferWritable {

    private long unknown1;
    private long unknown2;

    public GroupMissionCriticalObject(final ByteBuffer buffer) {
        unknown1 = buffer.getLong();
        unknown2 = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(unknown1);
        buffer.putLong(unknown2);
    }
}
