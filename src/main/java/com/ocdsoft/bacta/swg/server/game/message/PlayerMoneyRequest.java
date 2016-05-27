package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * SOECRC32.hashCode(PlayerMoneyRequest.class.getSimpleName()); // 0x9d105aa1
 */
@Getter
@Priority(0x1)
public final class PlayerMoneyRequest extends GameNetworkMessage {

    public PlayerMoneyRequest() {

    }

    public PlayerMoneyRequest(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
