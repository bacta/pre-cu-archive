package com.ocdsoft.bacta.swg.precu.object.archive.delta;

import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.AutoByteStream;
import com.ocdsoft.bacta.swg.precu.object.archive.OnDirtyCallbackBase;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by crush on 8/14/2014.
 */
public class AutoDeltaByteStream extends AutoByteStream {
    private transient final ServerObject parent;
    private transient final Set<AutoDeltaVariableBase> dirtyList = new TreeSet<>();
    private transient OnDirtyCallbackBase onDirtyCallback;

    public AutoDeltaByteStream(ServerObject parent) {
        this.parent = parent;
    }

    public void addToDirtyList(AutoDeltaVariableBase variable) {
        dirtyList.add(variable);

        if (onDirtyCallback != null)
            onDirtyCallback.onDirty(this.parent);
    }

    public boolean isDirty() {
        return dirtyList.size() > 0;
    }

    public void addOnDirtyCallback(OnDirtyCallbackBase onDirtyCallback) {
        this.onDirtyCallback = onDirtyCallback;
    }

    public void unpackDeltas(ByteBuffer buffer) {
        throw new RuntimeException("Not implemented.");
    }

    public void packDeltas(ByteBuffer buffer) {
        buffer.putShort((short)dirtyList.size());

        for (AutoDeltaVariableBase variable : dirtyList) {
            buffer.putShort((short)variable.getIndex());
            variable.packDelta(buffer);
        }

        dirtyList.clear();
    }

    public void clearDeltas() {
        for (AutoDeltaVariableBase variable : dirtyList)
            variable.clearDelta();

        dirtyList.clear();
    }

    public final void addVariable(final AutoDeltaVariableBase variable) {
        variable.setIndex(members.size());
        variable.setOwner(this);

        super.addVariable(variable);
    }
}
