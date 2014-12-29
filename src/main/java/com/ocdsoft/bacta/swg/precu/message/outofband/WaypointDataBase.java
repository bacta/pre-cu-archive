package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBufSerializable;
import com.ocdsoft.bacta.swg.server.game.object.Location;

public class WaypointDataBase implements SoeByteBufSerializable {
    private int appearanceNameCrc;
    private Location location;
    private String name;
    private byte color;
    private boolean active;

    public WaypointDataBase(SoeByteBuf message) {
        appearanceNameCrc = message.readInt();
        location = new Location(message);
        name = message.readUnicode();
        color = message.readByte();
        active = message.readBoolean();
    }

    @Override
    public void writeToBuffer(SoeByteBuf buffer) {
        buffer.writeInt(appearanceNameCrc);
        location.writeToBuffer(buffer);
        buffer.writeUnicode(name);
        buffer.writeByte(color);
        buffer.writeBoolean(active);
    }
}
