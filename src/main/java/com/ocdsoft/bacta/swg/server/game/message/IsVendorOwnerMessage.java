package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * B7 5F 72 00 00 00 00 00 00 5D 00
 * <p>
 * SOECRC32.hashCode(IsVendorOwnerMessage.class.getSimpleName()); // 0x21b55a3b
 */
@Getter
@Priority(0x2)
public final class IsVendorOwnerMessage extends GameNetworkMessage {

    public IsVendorOwnerMessage() {

    }

    public IsVendorOwnerMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
