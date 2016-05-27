package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * 0A 00 00 00 01 00 00 00
 * <p>
 * SOECRC32.hashCode(GuildRequestMessage.class.getSimpleName()); // 0x81eb4ef7
 */
@Getter
@Priority(0x2)
public final class GuildRequestMessage extends GameNetworkMessage {

    public GuildRequestMessage() {

    }

    public GuildRequestMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
