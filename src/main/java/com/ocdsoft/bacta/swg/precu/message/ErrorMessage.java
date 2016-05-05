package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
public final class ErrorMessage extends GameNetworkMessage {

    static {
        priority = 0x3;
        messageType = SOECRC32.hashCode(ErrorMessage.class.getSimpleName()); //0xB5ABF91A
    }

    private final String errorName;
    private final String description;
    private final boolean fatal;

    public ErrorMessage(final ByteBuffer buffer) {
        this.errorName = BufferUtil.getAscii(buffer);
        this.description = BufferUtil.getAscii(buffer);
        this.fatal = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, errorName);
        BufferUtil.putAscii(buffer, description);
        BufferUtil.putBoolean(buffer, fatal);
    }
}
