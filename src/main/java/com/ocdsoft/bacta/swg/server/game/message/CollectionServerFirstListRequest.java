package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/26/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class CollectionServerFirstListRequest extends GameNetworkMessage {
    private final String updateNumber;

    public CollectionServerFirstListRequest(final ByteBuffer buffer) {
        this.updateNumber = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, updateNumber);
    }
}