package com.ocdsoft.bacta.swg.precu.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;

import java.nio.ByteBuffer;

public final class AuctionToken implements ByteBufferSerializable {
    private String sharedTemplateName;
    private String customizationData;

    public AuctionToken(final String sharedTemplateName, final String customizationData) {
        this.sharedTemplateName = sharedTemplateName;
        this.customizationData = customizationData;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.sharedTemplateName = BufferUtil.getAscii(buffer);
        this.customizationData = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, sharedTemplateName);
        BufferUtil.putAscii(buffer, customizationData);
    }
}
