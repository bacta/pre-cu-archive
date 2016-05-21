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
public final class ChatOnKickAvatarFromRoom extends GameNetworkMessage {
    private final ChatAvatarId avatarId;
    private final ChatAvatarId removeId;
    private final int resultCode;
    private final String roomName;

    public ChatOnKickAvatarFromRoom(final ByteBuffer buffer) {
        this.avatarId = new ChatAvatarId(buffer);
        this.removeId = new ChatAvatarId(buffer);
        this.resultCode = buffer.getInt();
        this.roomName = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, avatarId);
        BufferUtil.put(buffer, removeId);
        BufferUtil.put(buffer, resultCode);
        BufferUtil.put(buffer, roomName);
    }
}