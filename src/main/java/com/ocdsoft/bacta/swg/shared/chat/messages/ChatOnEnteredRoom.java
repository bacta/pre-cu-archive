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
public final class ChatOnEnteredRoom extends GameNetworkMessage {
    private final int sequence;
    private final ChatResult result;
    private final int roomId;
    private final ChatAvatarId characterName;

    public ChatOnEnteredRoom(final ByteBuffer buffer) {
        this.characterName = new ChatAvatarId(buffer);
        this.result = ChatResult.from(buffer.getInt());
        this.roomId = buffer.getInt();
        this.sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterName);
        BufferUtil.put(buffer, result.value);
        BufferUtil.put(buffer, roomId);
        BufferUtil.put(buffer, sequence);
    }
}