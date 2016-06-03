package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * C3 00 00 00 01 00 00 00 00
 * <p>
 * SOECRC32.hashCode(ShowHelmet.class.getSimpleName()); // 0xddefec1d
 */
@Getter
@Priority(0x2)
public final class ShowHelmet extends GameNetworkMessage {

    public ShowHelmet() {

    }

    public ShowHelmet(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
