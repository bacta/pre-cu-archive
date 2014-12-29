package com.ocdsoft.bacta.swg.precu.object.archive;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

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

    public void pack(SoeByteBuf buffer) {
        buffer.writeShort(getItemCount());

        for (AutoVariableBase variable : members)
            variable.pack(buffer);
    }

    public void unpack(SoeByteBuf buffer) {
        throw new RuntimeException("Not implemented.");
    }
}
