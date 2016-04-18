package com.ocdsoft.bacta.swg.precu.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class DeleteCharacterReplyMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(DeleteCharacterReplyMessage.class.getSimpleName());

    private boolean success;

    public DeleteCharacterReplyMessage(boolean success) {
        super(priority, messageType);

        this.success = success;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.success = (buffer.getInt() == 1 ? true : false);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(success ? 0 : 1);
    }

}
