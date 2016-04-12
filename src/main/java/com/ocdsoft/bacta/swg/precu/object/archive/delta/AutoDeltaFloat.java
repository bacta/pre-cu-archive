package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import java.nio.ByteBuffer;

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
    public void packDelta(ByteBuffer buffer) {
        buffer.putFloat(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.getFloat();
    }

    @Override
    public void pack(ByteBuffer buffer) {
        buffer.putFloat(currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        currentValue = lastValue = buffer.getFloat();
    }
}
