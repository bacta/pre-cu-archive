package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;

public final class OutOfBand<T extends SoeByteBufSerializable> {
    private final T object;

    public OutOfBand(T object) {
        this.object = object;
    }
}
