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
public final class ChatOnReceiveRoomInvitation extends GameNetworkMessage {
    private final String roomName;
    private final ChatAvatarId inviterAvatarId;

    public ChatOnReceiveRoomInvitation(final ByteBuffer buffer) {
        inviterAvatarId = new ChatAvatarId(buffer);
        roomName = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, inviterAvatarId);
        BufferUtil.put(buffer, roomName);
    }
}