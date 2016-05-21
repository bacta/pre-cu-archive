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
 * The Chat Server is basically acknowledging that it received the room invitation request,
 * and it is sending back a result about whether or not invitation request was accepted for the
 * requested player.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatOnSendRoomInvitation extends GameNetworkMessage {
    private final int sequence;
    private final int result;

    public ChatOnSendRoomInvitation(final ByteBuffer buffer) {
        result = buffer.getInt();
        sequence = buffer.get();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, result);
        BufferUtil.put(buffer, sequence);
    }
}