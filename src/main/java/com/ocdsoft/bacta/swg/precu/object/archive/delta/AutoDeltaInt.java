package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import java.nio.ByteBuffer;

public class AutoDeltaInt extends AutoDeltaVariableBase {
    private int currentValue;
    private transient int lastValue;

    public AutoDeltaInt(int value, AutoDeltaByteStream owner) {
        super(owner);
        this.currentValue = this.lastValue = value;
    }

    public int get() {
        return currentValue;
    }

    public void set(int value) {
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
        buffer.putInt(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.getInt();
    }

    @Override
    public void pack(ByteBuffer buffer) {
        buffer.putInt(currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.getInt();
    }
}
