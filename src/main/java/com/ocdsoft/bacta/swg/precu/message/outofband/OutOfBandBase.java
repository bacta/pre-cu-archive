package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

public abstract class OutOfBandBase implements SoeByteBufSerializable {
    @Getter
    private final byte typeId;
    @Getter
    private final int position; //-1 for everything but -3 for to put waypoints in the attachment window.

    public OutOfBandBase(int type, int position) {
        this.typeId = (byte) type;
        this.position = position;
    }

    public OutOfBandBase(ByteBuf message) {
        int size = message.readInt();
        boolean odd = message.readShort() != 0;

        this.typeId = message.readByte();
        this.position = message.readInt();
    }

    @Override
    public void writeToBuffer(SoeByteBuf message) {
        message.writeByte(typeId);
        message.writeInt(position);
    }
}
