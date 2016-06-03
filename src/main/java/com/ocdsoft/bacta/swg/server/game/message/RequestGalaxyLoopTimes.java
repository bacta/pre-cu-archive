package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * SOECRC32.hashCode(RequestGalaxyLoopTimes.class.getSimpleName()); // 0x7d842d68
 */
@Getter
@Priority(0x1)
public final class RequestGalaxyLoopTimes extends GameNetworkMessage {

    public RequestGalaxyLoopTimes() {

    }

    public RequestGalaxyLoopTimes(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
