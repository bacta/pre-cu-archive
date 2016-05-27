package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * SOECRC32.hashCode(FactionRequestMessage.class.getSimpleName()); // 0xc1b03b81
 */
@Getter
@Priority(0x1)
public final class FactionRequestMessage extends GameNetworkMessage {

    public FactionRequestMessage() {

    }

    public FactionRequestMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
