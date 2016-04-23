package com.ocdsoft.bacta.swg.precu.message;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public final class ErrorMessage extends GameNetworkMessage {

    private String errorName;
    private String description;
    private boolean fatal;

    @Inject
    public ErrorMessage() {
        super(0x03, 0xB5ABF91A);

        this.errorName = "";
        this.description = "";
        this.fatal = false;
    }

    public ErrorMessage(final String errorName, final String description, boolean fatal) {
        super(0x03, 0xB5ABF91A);

        this.errorName = errorName;
        this.description = description;
        this.fatal = fatal;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.errorName = BufferUtil.getAscii(buffer);
        this.description = BufferUtil.getAscii(buffer);
        this.fatal = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, errorName);
        BufferUtil.putAscii(buffer, description);
        BufferUtil.putBoolean(buffer, fatal);
    }
}
