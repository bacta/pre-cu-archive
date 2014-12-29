package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

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
    public void packDelta(SoeByteBuf buffer) {
        buffer.writeByte(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readByte();
    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeByte(currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readByte();
    }
}
