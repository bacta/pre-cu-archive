package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

public class AutoDeltaFloat extends AutoDeltaVariableBase {
    private float currentValue;
    private transient float lastValue;

    public AutoDeltaFloat(float value, AutoDeltaByteStream owner) {
        super(owner);
        this.currentValue = this.lastValue = value;
    }

    public float get() {
        return currentValue;
    }

    public void set(float value) {
        currentValue = value;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    @Override
    public void clearDelta() {
        this.lastValue = this.currentValue;
    }

    public boolean isDirty() {
        return currentValue != lastValue;
    }

    @Override
    public void packDelta(SoeByteBuf buffer) {
        buffer.writeFloat(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readFloat();
    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeFloat(currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        currentValue = lastValue = buffer.readFloat();
    }
}
