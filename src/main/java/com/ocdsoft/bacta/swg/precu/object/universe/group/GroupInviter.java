package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/12/2014.
 */
@Getter
@AllArgsConstructor
public class GroupInviter implements ByteBufferWritable {

    private final long inviterId;
    private final long inviteCounter;

    public GroupInviter(ByteBuffer buffer) {
        inviterId = buffer.getLong();
        inviteCounter = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(inviterId);
        buffer.putLong(inviteCounter);
    }
}
