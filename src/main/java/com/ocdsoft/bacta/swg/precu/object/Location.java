package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import io.netty.buffer.ByteBuf;

/**
 * Created by crush on 8/13/2014.
 */
public class Location implements SoeByteBufSerializable {
    private Vector coordinates; //Vector
    private long cell; //NetworkId
    private int sceneIdCrc;

    public Location(ByteBuf message) {
        coordinates = new Vector(
                message.readFloat(),
                message.readFloat(),
                message.readFloat());

        cell = message.readLong();
        sceneIdCrc = message.readInt();
    }

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        coordinates.writeToBuffer(buffer);
        buffer.writeLong(cell);
        buffer.writeInt(sceneIdCrc);
    }
}
