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
public final class ChatOnAddModeratorToRoom extends GameNetworkMessage {
    private final ChatAvatarId avatarId;
    private final ChatAvatarId granterId;
    private final int resultCode;
    private final String roomName;
    private final int sequenceId;

    public ChatOnAddModeratorToRoom(final ByteBuffer buffer) {
        avatarId = new ChatAvatarId(buffer);
        granterId = new ChatAvatarId(buffer);
        resultCode = buffer.getInt();
        roomName = BufferUtil.getAscii(buffer);
        sequenceId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, avatarId);
        BufferUtil.put(buffer, granterId);
        BufferUtil.put(buffer, resultCode);
        BufferUtil.put(buffer, roomName);
        BufferUtil.put(buffer, sequenceId);
    }
}