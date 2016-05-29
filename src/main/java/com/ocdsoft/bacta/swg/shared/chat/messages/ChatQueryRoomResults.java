package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.shared.chat.ChatRoomData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatQueryRoomResults extends GameNetworkMessage {
    private final int sequence;
    private final ChatRoomData roomData;
    private final ChatAvatarId[] avatars;
    private final ChatAvatarId[] invitees;
    private final ChatAvatarId[] moderators;
    private final ChatAvatarId[] banned;

    public ChatQueryRoomResults(final ByteBuffer buffer) {
        int size = buffer.getInt();
        avatars = new ChatAvatarId[size];

        for (int i = 0; i < size; ++i)
            avatars[i] = new ChatAvatarId(buffer);

        size = buffer.getInt();
        invitees = new ChatAvatarId[size];

        for (int i = 0; i < size; ++i)
            invitees[i] = new ChatAvatarId(buffer);

        size = buffer.getInt();
        moderators = new ChatAvatarId[size];

        for (int i = 0; i < size; ++i)
            moderators[i] = new ChatAvatarId(buffer);

        size = buffer.getInt();
        banned = new ChatAvatarId[size];

        for (int i = 0; i < size; ++i)
            banned[i] = new ChatAvatarId(buffer);

        sequence = buffer.getInt();
        roomData = new ChatRoomData(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        int size = avatars == null ? 0 : avatars.length;
        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, avatars[i]);

        size = invitees == null ? 0 : invitees.length;
        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, invitees[i]);

        size = moderators == null ? 0 : moderators.length;
        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, moderators[i]);

        size = banned == null ? 0 : banned.length;
        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, banned[i]);

        BufferUtil.put(buffer, sequence);
        BufferUtil.put(buffer, roomData);
    }
}