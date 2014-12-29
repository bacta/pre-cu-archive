package com.ocdsoft.bacta.swg.precu.object.archive;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.network.buffer.ByteBufSerializable;

/**
 * Created by crush on 8/14/2014.
 */
public class AutoVariable<T extends ByteBufSerializable> implements AutoVariableBase {
    private T value;

    public AutoVariable(T value) {
        this.value = value;
    }

    public void set(T value) { this.value = value; }
    public T get() { return value; }

    @Override
    public void pack(SoeByteBuf buffer) {
        value.writeToBuffer(buffer);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {

    }
}
