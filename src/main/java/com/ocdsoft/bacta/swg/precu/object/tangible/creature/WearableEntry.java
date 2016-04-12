package com.ocdsoft.bacta.swg.precu.object.tangible.creature;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Data;
import lombok.Getter;

import java.nio.Buffer;
import java.nio.ByteBuffer;

@Getter
public class WearableEntry implements ByteBufferSerializable {

    private String customizationString;
    private int containmentType;
    private long objectId;
    private int templateCrc;

    public WearableEntry(final String customizationString, final int containmentType, final long objectId, final int templateCrc) {
        this.customizationString = customizationString;
        this.containmentType = containmentType;
        this.objectId = objectId;
        this.templateCrc = templateCrc;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.customizationString = BufferUtil.getAscii(buffer);
        this.containmentType = buffer.getInt();
        this.objectId = buffer.getLong();
        this.templateCrc = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, customizationString);
        buffer.putInt(containmentType);
        buffer.putLong(objectId);
        buffer.putInt(templateCrc);
    }
}
