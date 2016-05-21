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
public final class ChatCreateRoom extends GameNetworkMessage {
    private final boolean publicRoom;
    private final boolean moderatedRoom;
    private final String ownerName;
    private final String roomName;
    private final String roomTitle;
    private final int sequence;

    public ChatCreateRoom(final ByteBuffer buffer) {
        publicRoom = BufferUtil.getBoolean(buffer);
        moderatedRoom = BufferUtil.getBoolean(buffer);
        ownerName = BufferUtil.getAscii(buffer);
        roomName = BufferUtil.getAscii(buffer);
        roomTitle = BufferUtil.getAscii(buffer);
        sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, publicRoom);
        BufferUtil.put(buffer, moderatedRoom);
        BufferUtil.put(buffer, ownerName);
        BufferUtil.put(buffer, roomName);
        BufferUtil.put(buffer, roomTitle);
        BufferUtil.put(buffer, publicRoom);
    }
}