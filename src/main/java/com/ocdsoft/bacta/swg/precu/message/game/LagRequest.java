package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
      01 00 E0 5E 80 31 

  */
public class LagRequest extends GameNetworkMessage {

    private static final short priority = 0x1;
    private static final int messageType = SOECRC32.hashCode(LagRequest.class.getSimpleName()); // 0x31805ee0

    @Inject
    public LagRequest() {
        super(priority, messageType);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
