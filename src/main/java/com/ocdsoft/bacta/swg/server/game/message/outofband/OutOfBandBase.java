package com.ocdsoft.bacta.swg.server.game.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public abstract class OutOfBandBase implements ByteBufferWritable {

    private final byte typeId;
    private final int position; //-1 for everything but -3 for to put waypoints in the attachment window.

    public OutOfBandBase(final ByteBuffer buffer) {
        // TODO: what is this?
//        int size = buffer.getInt();
//        boolean odd = buffer.getShort() != 0;

        this.typeId = buffer.get();
        this.position = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.put(typeId);
        buffer.putInt(position);
    }
}
