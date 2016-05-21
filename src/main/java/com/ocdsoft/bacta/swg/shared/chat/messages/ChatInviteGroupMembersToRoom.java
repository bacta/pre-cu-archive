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
public final class ChatInviteGroupMembersToRoom extends GameNetworkMessage {
    private final long inviterNetworkId;
    private final ChatAvatarId groupLeaderId;
    private final String roomName;
    private final long[] invitedMembers;

    public ChatInviteGroupMembersToRoom(final ByteBuffer buffer) {
        this.inviterNetworkId = buffer.getLong();
        this.groupLeaderId = new ChatAvatarId(buffer);
        this.roomName = BufferUtil.getAscii(buffer);

        final int size = buffer.getInt();
        invitedMembers = new long[size];

        for (int i = 0; i < size; ++i)
            invitedMembers[i] = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, inviterNetworkId);
        BufferUtil.put(buffer, groupLeaderId);
        BufferUtil.put(buffer, roomName);

        final int size = invitedMembers.length;

        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, invitedMembers[i]);
    }
}