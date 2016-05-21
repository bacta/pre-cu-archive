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
public final class ChatRoomMessage extends GameNetworkMessage {
    private final int roomId;
    private final ChatAvatarId from;
    private final String message;
    private final String outOfBand;

    public ChatRoomMessage(final ByteBuffer buffer) {
        this.from = new ChatAvatarId(buffer);
        this.roomId = buffer.getInt();
        this.message = BufferUtil.getUnicode(buffer);
        this.outOfBand = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, from);
        BufferUtil.put(buffer, roomId);
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.putUnicode(buffer, outOfBand);
    }
}