package com.ocdsoft.bacta.swg.precu.object.archive.delta;


import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;

import java.nio.ByteBuffer;

public class AutoDeltaVariable<T extends ByteBufferWritable> extends AutoDeltaVariableBase {
    private T currentValue;
    private transient int lastValue;

    public AutoDeltaVariable(T value, AutoDeltaByteStream owner) {
        super(owner);

        this.currentValue = value;
        this.lastValue = value.hashCode();
    }

    public T get() {
        return this.currentValue;
    }

    public void set(T value) {
        this.currentValue = value;

        if (owner != null)
            owner.addToDirtyList(this);
    }

    @Override
    public void clearDelta() {
        this.lastValue = this.currentValue.hashCode();
    }

    public boolean isDirty() {
        return this.lastValue != this.currentValue.hashCode();
    }

    @Override
    public void packDelta(ByteBuffer buffer) {
        this.currentValue.writeToBuffer(buffer);
        clearDelta();
    }

    @Override
    public void unpackDelta(ByteBuffer buffer) {

    }

    @Override
    public void pack(ByteBuffer buffer) {
        currentValue.writeToBuffer(buffer);
    }

    @Override
    public void unpack(ByteBuffer buffer) {
        //this.currentValue.readFromBuffer(buffer);
        //this.lastValue = this.currentValue;
    }
}
