package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import java.nio.ByteBuffer;

public class AutoDeltaByte extends AutoDeltaVariableBase {
    private byte currentValue;
    private transient byte lastValue;

    public AutoDeltaByte(int value, AutoDeltaByteStream owner) {
        super(owner);
        this.currentValue = this.lastValue = (byte) value;
    }

    public byte get() {
        return currentValue;
    }

    public void set(int value) {
        this.currentValue = (byte) value;

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
        buffer.put(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.get();
    }

    @Override
    public void pack(ByteBuffer buffer) {
        buffer.put(currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.get();
    }
}
