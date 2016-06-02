package com.ocdsoft.bacta.swg.server.game.biography;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 6/1/2016.
 * <p>
 * Sent to the client as part of a GenericValueTypeMessage.
 */
@Getter
@AllArgsConstructor
public final class BiographyPayload implements ByteBufferWritable {
    private final long networkId;
    private final String biography;

    public BiographyPayload(final ByteBuffer buffer) {
        this.networkId = buffer.getLong();
        this.biography = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, networkId);
        BufferUtil.putUnicode(buffer, biography);
    }
}
