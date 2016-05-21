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
public final class ChatOnInviteToRoom extends GameNetworkMessage {
    private final String roomName;
    private final ChatAvatarId inviter;
    private final ChatAvatarId invitee;
    private final int result;

    public ChatOnInviteToRoom(final ByteBuffer buffer) {
        this.roomName = BufferUtil.getAscii(buffer);
        this.inviter = new ChatAvatarId(buffer);
        this.invitee = new ChatAvatarId(buffer);
        this.result = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, roomName);
        BufferUtil.put(buffer, inviter);
        BufferUtil.put(buffer, invitee);
        BufferUtil.put(buffer, result);
    }
}