package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import java.nio.ByteBuffer;

public class AutoDeltaLong extends AutoDeltaVariableBase {
    private long currentValue;
    private transient long lastValue;

    public AutoDeltaLong(long value, AutoDeltaByteStream owner) {
        super(owner);
        this.currentValue = this.lastValue = value;
    }

    public long get() {
        return currentValue;
    }

    public void set(long value) {
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
        buffer.putLong(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.getLong();

    }

    @Override
    public void pack(ByteBuffer buffer) {
        buffer.putLong(currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.getLong();
    }
}
