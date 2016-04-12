package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import java.nio.ByteBuffer;

public class AutoDeltaShort extends AutoDeltaVariableBase {
    private short currentValue;
    private transient short lastValue;

    public AutoDeltaShort(int value, AutoDeltaByteStream owner) {
        super(owner);
        this.currentValue = this.lastValue = (short) value;
    }

    public short get() {
        return currentValue;
    }

    public void set(int value) {
        currentValue = (short) value;

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
        buffer.putShort(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {

    }

    @Override
    public void pack(ByteBuffer buffer) {
        buffer.putShort(currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        this.currentValue = this.lastValue = buffer.getShort();
    }
}
