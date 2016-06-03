package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * C3 00 00 00 01 00 00 00 00
 * <p>
 * SOECRC32.hashCode(ShowBackpack.class.getSimpleName()); // 0x8824757b
 */
@Getter
@Priority(0x2)
public final class ShowBackpack extends GameNetworkMessage {

    public ShowBackpack() {

    }

    public ShowBackpack(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
