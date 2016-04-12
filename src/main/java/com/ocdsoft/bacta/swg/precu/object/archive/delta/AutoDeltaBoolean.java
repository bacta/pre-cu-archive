package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.engine.utils.BufferUtil;

import java.nio.ByteBuffer;

public class AutoDeltaBoolean extends AutoDeltaVariableBase {
    private boolean currentValue;
    private transient boolean lastValue;

    public AutoDeltaBoolean(boolean value, AutoDeltaByteStream owner) {
        super(owner);

        this.currentValue = this.lastValue = value;
    }

    public boolean get() {
        return this.currentValue;
    }

    public void set(boolean value) {
        this.currentValue = value;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    public void clearDelta() {
        this.lastValue = this.currentValue;
    }

    public boolean isDirty() {
        return this.currentValue != this.lastValue;
    }

    @Override
    public void packDelta(ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, this.currentValue);

        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {
        this.currentValue = this.lastValue = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void pack(ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, this.currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        this.currentValue = this.lastValue = BufferUtil.getBoolean(buffer);
    }
}
