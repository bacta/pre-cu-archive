package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;

public abstract class OutOfBandBase implements ByteBufferSerializable {
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
    public void writeToBuffer(ByteBuffer message) {
        message.put(typeId);
        message.putInt(position);
    }
}
