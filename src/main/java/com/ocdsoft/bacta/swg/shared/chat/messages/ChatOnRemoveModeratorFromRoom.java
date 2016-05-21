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
public final class ChatOnRemoveModeratorFromRoom extends GameNetworkMessage {
    private final int resultCode;
    private final int sequenceId;
    private final ChatAvatarId avatarId;
    private final ChatAvatarId removerId;
    private final String roomName;

    public ChatOnRemoveModeratorFromRoom(final ByteBuffer buffer) {
        this.avatarId = new ChatAvatarId(buffer);
        this.removerId = new ChatAvatarId(buffer);
        this.resultCode = buffer.getInt();
        this.roomName = BufferUtil.getAscii(buffer);
        this.sequenceId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, avatarId);
        BufferUtil.put(buffer, removerId);
        BufferUtil.put(buffer, resultCode);
        BufferUtil.put(buffer, roomName);
        BufferUtil.put(buffer, sequenceId);
    }
}