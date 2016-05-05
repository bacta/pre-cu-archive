package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
      01 00 E0 5E 80 31 

  */
public final class LagRequest extends GameNetworkMessage {

    static {
        priority = 0x1;
        messageType = SOECRC32.hashCode(LagRequest.class.getSimpleName()); // 0x31805ee0
    }

    public LagRequest(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }
}
