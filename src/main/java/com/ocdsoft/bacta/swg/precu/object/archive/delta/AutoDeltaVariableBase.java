package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.precu.object.archive.AutoVariableBase;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/14/2014.
 */
public abstract class AutoDeltaVariableBase implements AutoVariableBase, Comparable<AutoDeltaVariableBase> {

    @Setter protected AutoDeltaByteStream owner;
    @Getter @Setter protected int index;

    public AutoDeltaVariableBase(AutoDeltaByteStream owner) {
        owner.addVariable(this);
    }

    public abstract void clearDelta();
    public abstract boolean isDirty();
    public abstract void packDelta(ByteBuffer buffer);
    public abstract void unpackDelta(ByteBuffer buffer);

    @Override
    public int compareTo(AutoDeltaVariableBase other) {
        return other.index - index;
    }
}
