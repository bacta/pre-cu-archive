package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;

/**
 * Created by crush on 8/13/2014.
 */
public class Vector implements SoeByteBufSerializable {
    public static final Vector zero = new Vector(0, 0, 0);

    private final float x;
    private final float y;
    private final float z;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        buffer.writeFloat(x);
        buffer.writeFloat(y);
        buffer.writeFloat(z);
    }
}
