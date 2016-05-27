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
public final class ChatChangeFriendStatus extends GameNetworkMessage {
    private final int sequence;
    private final ChatAvatarId characterId;
    private final ChatAvatarId friendId;
    private final boolean add;

    public ChatChangeFriendStatus(final ByteBuffer buffer) {
        this.characterId = new ChatAvatarId(buffer);
        this.friendId = new ChatAvatarId(buffer);
        this.sequence = buffer.getInt();
        this.add = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterId);
        BufferUtil.put(buffer, friendId);
        BufferUtil.put(buffer, sequence);
        BufferUtil.put(buffer, add);
    }
}