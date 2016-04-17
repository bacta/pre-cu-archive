package com.ocdsoft.bacta.swg.precu.object.archive;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crush on 8/13/2014.
 */
public class AutoByteStream {
    protected final List<AutoVariableBase> members = new ArrayList<>();

    public int getItemCount() {
        return members.size();
    }

    public final void addVariable(final AutoVariableBase variable) {
        members.add(variable);
    }

    public void pack(ByteBuffer buffer) {
        buffer.putShort((short) getItemCount());

        for (AutoVariableBase variable : members) {
            variable.pack(buffer);
        }
    }

    public void unpack(ByteBuffer buffer) {
        throw new RuntimeException("Not implemented.");
    }
}
