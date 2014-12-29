package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

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
    public void packDelta(SoeByteBuf buffer) {
        buffer.writeInt(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {

    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeInt(currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readInt();
    }
}
