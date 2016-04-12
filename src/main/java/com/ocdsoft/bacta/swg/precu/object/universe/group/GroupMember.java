package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class GroupMember implements ByteBufferSerializable {
    @Getter private long memberId;
    @Getter private String memberName;


    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        memberId = buffer.getLong();
        memberName = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(memberId);
        BufferUtil.putAscii(buffer, memberName);
    }
}
