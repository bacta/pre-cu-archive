package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;

public final class OutOfBand<T extends ByteBufferSerializable> {
    private final T object;

    public OutOfBand(T object) {
        this.object = object;
    }
}
