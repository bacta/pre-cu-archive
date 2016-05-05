package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
      01 00 E0 5E 80 31 

  */
@Priority(0x1)
public final class LagRequest extends GameNetworkMessage {

    public LagRequest(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }
}
