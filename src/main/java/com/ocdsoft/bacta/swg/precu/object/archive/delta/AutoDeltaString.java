package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.engine.utils.BufferUtil;

import java.nio.ByteBuffer;

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
    public void packDelta(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, currentValue);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {

    }

    @Override
    public void pack(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, currentValue);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        this.currentValue = this.lastValue = BufferUtil.getAscii(buffer);
    }
}
