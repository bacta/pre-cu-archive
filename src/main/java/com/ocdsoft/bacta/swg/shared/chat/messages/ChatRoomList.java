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
public final class ChatRoomList extends GameNetworkMessage {
    private final ChatRoomData[] roomData;

    public ChatRoomList(final ByteBuffer buffer) {
        final int size = buffer.getInt();

        roomData = new ChatRoomData[size];

        for (int i = 0; i < size; ++i)
            roomData[i] = new ChatRoomData(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        final int size = roomData.length;

        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, roomData[i]);
    }
}