package com.ocdsoft.bacta.swg.server.game.object.tangible.creature;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public final class WearableEntry implements ByteBufferWritable {

    private final String customizationString;
    private final int containmentType;
    private final long objectId;
    private final int templateCrc;

    public WearableEntry(final ByteBuffer buffer) {
        this.customizationString = BufferUtil.getAscii(buffer);
        this.containmentType = buffer.getInt();
        this.objectId = buffer.getLong();
        this.templateCrc = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, customizationString);
        buffer.putInt(containmentType);
        buffer.putLong(objectId);
        buffer.putInt(templateCrc);
    }
}
