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
public final class ChatDestroyRoom extends GameNetworkMessage {
    private final int roomId;
    private final int sequence;

    public ChatDestroyRoom(final ByteBuffer buffer) {
        roomId = buffer.getInt();
        sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(roomId);
        buffer.putInt(sequence);
    }
}