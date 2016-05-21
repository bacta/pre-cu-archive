package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 * <p>
 * The Chat Server is acknowledging that it received the instant message
 * and returns a result about what it did with the message.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatOnSendInstantMessage extends GameNetworkMessage {
    private final int sequence;
    private final int result;

    public ChatOnSendInstantMessage(final ByteBuffer buffer) {
        result = buffer.getInt();
        sequence = buffer.get();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, result);
        BufferUtil.put(buffer, sequence);
    }
}