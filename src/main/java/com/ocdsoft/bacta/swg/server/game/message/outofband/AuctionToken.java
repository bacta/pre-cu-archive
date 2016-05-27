package com.ocdsoft.bacta.swg.server.game.message.outofband;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public final class AuctionToken implements ByteBufferWritable {
    private final String sharedTemplateName;
    private final String customizationData;

    public AuctionToken(final ByteBuffer buffer) {
        this.sharedTemplateName = BufferUtil.getAscii(buffer);
        this.customizationData = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, sharedTemplateName);
        BufferUtil.putAscii(buffer, customizationData);
    }
}
