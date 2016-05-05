package com.ocdsoft.bacta.swg.precu.message.game.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public class WaypointDataBase implements ByteBufferWritable {

    private final int appearanceNameCrc;
    private final Vector location;
    private final String name;
    private final byte color;
    private final boolean active;

    public WaypointDataBase(ByteBuffer buffer) {
        appearanceNameCrc = buffer.getInt();
        location = new Vector(buffer);
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
