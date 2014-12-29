package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

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
    public void packDelta(SoeByteBuf buffer) {
        buffer.writeShort(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {

    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeShort(currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readShort();
    }
}
