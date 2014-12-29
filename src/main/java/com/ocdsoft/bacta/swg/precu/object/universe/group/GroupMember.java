package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import lombok.Getter;

public class GroupMember implements SoeByteBufSerializable {
    @Getter private long memberId;
    @Getter private String memberName;

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        buffer.writeLong(memberId);
        buffer.writeAscii(memberName);
    }
}
