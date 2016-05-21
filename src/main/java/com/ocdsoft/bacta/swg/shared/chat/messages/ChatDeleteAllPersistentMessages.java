package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatDeleteAllPersistentMessages extends GameNetworkMessage {
    private final long sourceNetworkId;
    private final long targetNetworkId;

    public ChatDeleteAllPersistentMessages(final ByteBuffer buffer) {
        this.sourceNetworkId = buffer.getLong();
        this.targetNetworkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(sourceNetworkId);
        buffer.putLong(targetNetworkId);
    }
}