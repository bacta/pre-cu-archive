package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

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
    public void packDelta(SoeByteBuf buffer) {
        buffer.writeBoolean(this.currentValue);

        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readBoolean();
    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeBoolean(this.currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readBoolean();
    }
}
