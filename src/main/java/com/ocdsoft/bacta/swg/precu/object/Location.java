package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import io.netty.buffer.ByteBuf;

import javax.vecmath.Vector3f;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by crush on 8/13/2014.
 */
public class Location implements ByteBufferSerializable {
    private Vector3f coordinates; //Vector
    private long cell; //NetworkId
    private int sceneIdCrc;

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        coordinates = new Vector3f(
                buffer.getFloat(),
                buffer.getFloat(),
                buffer.getFloat());

        cell = buffer.getLong();
        sceneIdCrc = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putVector3f(buffer, coordinates);
        buffer.putLong(cell);
        buffer.putInt(sceneIdCrc);
    }
}
