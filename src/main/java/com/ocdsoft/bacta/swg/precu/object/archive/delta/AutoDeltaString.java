package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

public class AutoDeltaString extends AutoDeltaVariableBase {
    private String currentValue;
    private transient String lastValue;

    public AutoDeltaString(final String value, AutoDeltaByteStream owner) {
        super(owner);
        this.currentValue = this.lastValue = value;
    }

    public String get() {
        return currentValue;
    }

    public void set(final String value) {
        this.currentValue = value;

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
        buffer.writeAscii(currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(SoeByteBuf buffer) {

    }

    @Override
    public void pack(SoeByteBuf buffer) {
        buffer.writeAscii(currentValue);
    }

    @Override
    public void unpack(SoeByteBuf buffer) {
        this.currentValue = this.lastValue = buffer.readAscii();
    }
}
