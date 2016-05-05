package com.ocdsoft.bacta.swg.precu.message.game.outofband;


import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;

public final class OutOfBand<T extends ByteBufferWritable> {
    private final T object;

    public OutOfBand(T object) {
        this.object = object;
    }
}
