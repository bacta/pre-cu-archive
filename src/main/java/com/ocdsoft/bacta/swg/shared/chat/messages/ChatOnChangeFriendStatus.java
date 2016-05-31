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
public final class ChatOnChangeFriendStatus extends GameNetworkMessage {
    private final int sequence;
    private final long characterNetworkId;
    private final ChatAvatarId friendId;
    private final boolean add;
    private final ChatResult resultCode;

    public ChatOnChangeFriendStatus(final ByteBuffer buffer) {
        characterNetworkId = buffer.getLong();
        friendId = new ChatAvatarId(buffer);
        sequence = buffer.getInt();
        add = BufferUtil.getBoolean(buffer);
        resultCode = ChatResult.from(buffer.getInt());
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterNetworkId);
        BufferUtil.put(buffer, friendId);
        BufferUtil.put(buffer, sequence);
        BufferUtil.put(buffer, add);
        BufferUtil.put(buffer, resultCode.value);
    }
}