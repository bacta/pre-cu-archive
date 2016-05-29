package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.shared.chat.ChatRoomData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatOnCreateRoom extends GameNetworkMessage {
    private final int sequence;
    private final int resultCode;
    private final ChatRoomData roomData;

    public ChatOnCreateRoom(final ByteBuffer buffer) {
        this.resultCode = buffer.getInt();
        this.roomData = new ChatRoomData(buffer);
        this.sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, resultCode);
        BufferUtil.put(buffer, roomData);
        BufferUtil.put(buffer, sequence);
    }
}