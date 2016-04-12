package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/12/2014.
 */
public class GroupInviter implements ByteBufferSerializable {

    @Getter
    @Setter
    private long inviterId = 0;

    @Getter
    @Setter
    private long inviteCounter = 0;


    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        inviterId = buffer.getLong();
        inviteCounter = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(inviterId);
        buffer.putLong(inviteCounter);
    }
}
