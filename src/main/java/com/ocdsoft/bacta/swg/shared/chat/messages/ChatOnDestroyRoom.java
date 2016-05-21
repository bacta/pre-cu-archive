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
public final class ChatOnDestroyRoom extends GameNetworkMessage {
    private final ChatAvatarId destroyer;
    private final int resultCode;
    private final int roomId;
    private final int sequence;

    public ChatOnDestroyRoom(final ByteBuffer buffer) {
        this.destroyer = new ChatAvatarId(buffer);
        this.resultCode = buffer.getInt();
        this.roomId = buffer.getInt();
        this.sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, destroyer);
        BufferUtil.put(buffer, resultCode);
        BufferUtil.put(buffer, roomId);
        BufferUtil.put(buffer, sequence);
    }
}