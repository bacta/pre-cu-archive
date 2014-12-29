package com.ocdsoft.bacta.swg.precu.object.archive;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;

/**
 * Created by crush on 8/13/2014.
 */
public interface AutoVariableBase {
    void pack(SoeByteBuf buffer);
    void unpack(SoeByteBuf buffer);
}
