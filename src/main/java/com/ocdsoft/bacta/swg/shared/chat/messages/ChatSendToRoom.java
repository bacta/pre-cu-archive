package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
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
public final class ChatSendToRoom extends GameNetworkMessage {
    private final int sequence;
    private final int roomId;
    private final String message; //unicode
    private final String outOfBand; //unicode

    public ChatSendToRoom(final ByteBuffer buffer) {
        message = BufferUtil.getUnicode(buffer);
        outOfBand = BufferUtil.getUnicode(buffer);
        roomId = buffer.getInt();
        sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.putUnicode(buffer, outOfBand);
        BufferUtil.put(buffer, roomId);
        BufferUtil.put(buffer, sequence);
    }
}