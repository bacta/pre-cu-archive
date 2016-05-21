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
public final class ChatOnGetFriendsList extends GameNetworkMessage {
    private final long characterId;
    private final ChatAvatarId[] friends;

    public ChatOnGetFriendsList(final ByteBuffer buffer) {
        this.characterId = buffer.getLong();

        final int size = buffer.getInt();

        this.friends = new ChatAvatarId[size];

        for (int i = 0; i < size; ++i)
            this.friends[i] = new ChatAvatarId(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterId);

        final int size = friends.length;

        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, friends[i]);
    }
}