package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@Priority(0x05)
public final class ChatPersistentMessageToClient extends GameNetworkMessage {
    private final ChatPersistentMessageToClientData data;

    public ChatPersistentMessageToClient(final int id, final byte status, final String fromGameCode, final String fromServerCode, final String fromCharacterName, final String subject, final int timeStamp) {
        this.data = new ChatPersistentMessageToClientData(
                fromCharacterName,
                fromGameCode,
                fromServerCode,
                id,
                true,
                "",
                subject,
                "",
                status,
                timeStamp);
    }

    public ChatPersistentMessageToClient(final int id, final byte status, final String fromGameCode, final String fromServerCode, final String fromCharacterName, final String subject, final String message, final String outOfBand, final int timeStamp) {
        this.data = new ChatPersistentMessageToClientData(
                fromCharacterName,
                fromGameCode,
                fromServerCode,
                id,
                false,
                subject,
                message,
                outOfBand,
                status,
                timeStamp);
    }

    public ChatPersistentMessageToClient(final ByteBuffer buffer) {
        this.data = new ChatPersistentMessageToClientData(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, data);
    }
}