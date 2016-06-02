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
public final class ChatOnChangeIgnoreStatus extends GameNetworkMessage {
    private final int sequence;
    private final long characterNetworkId;
    private final ChatAvatarId ignoreId;
    private final boolean ignore;
    private final ChatResult result;

    public ChatOnChangeIgnoreStatus(final ByteBuffer buffer) {
        characterNetworkId = buffer.getLong();
        ignoreId = new ChatAvatarId(buffer);
        sequence = buffer.getInt();
        ignore = BufferUtil.getBoolean(buffer);
        result = ChatResult.from(buffer.getInt());
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterNetworkId);
        BufferUtil.put(buffer, ignoreId);
        BufferUtil.put(buffer, sequence);
        BufferUtil.put(buffer, ignore);
        BufferUtil.put(buffer, result.value);
    }
}