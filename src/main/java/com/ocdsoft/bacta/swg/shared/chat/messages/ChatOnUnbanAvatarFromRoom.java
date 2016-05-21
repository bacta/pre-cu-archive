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
public final class ChatOnUnbanAvatarFromRoom extends GameNetworkMessage {
    private final int sequence;
    private final int result;
    private final String roomName;
    private final ChatAvatarId banner;
    private final ChatAvatarId bannee;


    public ChatOnUnbanAvatarFromRoom(final ByteBuffer buffer) {
        roomName = BufferUtil.getAscii(buffer);
        banner = new ChatAvatarId(buffer);
        bannee = new ChatAvatarId(buffer);
        result = buffer.getInt();
        sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, roomName);
        BufferUtil.put(buffer, banner);
        BufferUtil.put(buffer, bannee);
        BufferUtil.put(buffer, result);
        BufferUtil.put(buffer, sequence);
    }
}