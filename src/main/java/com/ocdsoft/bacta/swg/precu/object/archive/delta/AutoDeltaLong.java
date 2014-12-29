package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

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
    public void packDelta(SoeByteBuf buffer) {
        buffer.writeLong(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {

    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeLong(currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readLong();
    }
}
