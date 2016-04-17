package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public final class ErrorMessage extends GameNetworkMessage {

    public ErrorMessage(final String errorName, final String description, boolean fatal) {
        super(0x03, 0xB5ABF91A);

        writeAscii(errorName);
        writeAscii(description);
        writeBoolean(fatal);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
