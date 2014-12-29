package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.network.buffer.ByteBufSerializable;

public class AutoDeltaVariable<T extends ByteBufSerializable> extends AutoDeltaVariableBase {
    private T currentValue;
    private transient int lastValue;

    public AutoDeltaVariable(T value, AutoDeltaByteStream owner) {
        super(owner);

        this.currentValue = value;
        this.lastValue = value.hashCode();
    }

    public T get() {
        return this.currentValue;
    }

    public void set(T value) {
        this.currentValue = value;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    @Override
    public void clearDelta() {
        this.lastValue = this.currentValue.hashCode();
    }

    public boolean isDirty() {
        return this.lastValue != this.currentValue.hashCode();
    }

    @Override
    public void packDelta(SoeByteBuf buffer) {
        this.currentValue.writeToBuffer(buffer);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {

    }

    @Override
    public void pack(SoeByteBuf buffer) {
        currentValue.writeToBuffer(buffer);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        //this.currentValue.readFromBuffer(buffer);
        //this.lastValue = this.currentValue;
    }
}
