package com.ocdsoft.bacta.swg.precu.message.game.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.math.Vector;

import java.nio.ByteBuffer;

public class WaypointDataBase implements ByteBufferSerializable {

    private int appearanceNameCrc;
    private Vector location;
    private String name;
    private byte color;
    private boolean active;

    public WaypointDataBase() {
        location = new Vector();
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        appearanceNameCrc = buffer.getInt();
        location.readFromBuffer(buffer);
        name = BufferUtil.getUnicode(buffer);
        color = buffer.get();
        active = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(appearanceNameCrc);
        location.writeToBuffer(buffer);
        BufferUtil.putUnicode(buffer, name);
        buffer.put(color);
        BufferUtil.putBoolean(buffer, active);
    }
}