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
 * The Chat Server is basically acknowledging that it received the message,
 * and it is sending back a result about whether or not the message was
 * deliverable, or suffered some kind of error. This goes back to the player
 * who sent the original message.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatOnSendRoomMessage extends GameNetworkMessage {
    private final int sequence;
    private final int result;

    public ChatOnSendRoomMessage(final ByteBuffer buffer) {
        result = buffer.getInt();
        sequence = buffer.get();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, result);
        BufferUtil.put(buffer, sequence);
    }
}