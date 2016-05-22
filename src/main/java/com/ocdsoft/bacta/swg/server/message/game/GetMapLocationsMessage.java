package com.ocdsoft.bacta.swg.server.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * 08 00 74 61 74 6F 6F 69 6E 65 00 00 00 00 00 00
 * 00 00 00 00 00 00
 * <p>
 * SOECRC32.hashCode(GetMapLocationsMessage.class.getSimpleName()); // 0x1a7ab839
 */
@Getter
@Priority(0x5)
public final class GetMapLocationsMessage extends GameNetworkMessage {

    public GetMapLocationsMessage() {

    }

    public GetMapLocationsMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
