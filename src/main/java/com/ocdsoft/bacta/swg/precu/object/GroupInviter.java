package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by crush on 8/12/2014.
 */
public class GroupInviter implements SoeByteBufSerializable {

    @Getter
    @Setter
    private long inviterId = 0;

    @Getter
    @Setter
    private long inviteCounter = 0;

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        buffer.writeLong(inviterId);
        buffer.writeLong(inviteCounter);
    }
}
