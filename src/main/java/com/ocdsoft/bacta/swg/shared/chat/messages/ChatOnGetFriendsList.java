package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Set;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatOnGetFriendsList extends GameNetworkMessage {
    private final long characterId;
    private final Set<ChatAvatarId> friends;

    public ChatOnGetFriendsList(final ByteBuffer buffer) {
        this.characterId = buffer.getLong();
        this.friends = BufferUtil.getHashSet(buffer, ChatAvatarId::new);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterId);
        BufferUtil.put(buffer, friends);
    }
}