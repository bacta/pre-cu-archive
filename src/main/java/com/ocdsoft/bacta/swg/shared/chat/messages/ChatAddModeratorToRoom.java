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
public final class ChatAddModeratorToRoom extends GameNetworkMessage {
    private final ChatAvatarId avatarId;
    private final String roomName;
    private final int sequenceId;

    public ChatAddModeratorToRoom(final ByteBuffer buffer) {
        this.avatarId = new ChatAvatarId(buffer);
        this.roomName = BufferUtil.getAscii(buffer);
        this.sequenceId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        avatarId.writeToBuffer(buffer);
        BufferUtil.putAscii(buffer, roomName);
        BufferUtil.put(buffer, sequenceId);
    }
}
