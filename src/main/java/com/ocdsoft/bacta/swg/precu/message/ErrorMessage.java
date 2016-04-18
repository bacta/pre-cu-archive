package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.message.login.LoginClusterStatus;

import java.nio.ByteBuffer;

public final class ErrorMessage extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(ErrorMessage.class.getSimpleName());

    private String errorName;
    private String description;
    private boolean fatal;

    public ErrorMessage(final String errorName, final String description, boolean fatal) {
        super(priority, messageType);

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
