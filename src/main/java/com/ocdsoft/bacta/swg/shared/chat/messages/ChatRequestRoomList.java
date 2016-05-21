package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 * <p>
 * This is an empty message that just asks the server to send the list of rooms.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatRequestRoomList extends GameNetworkMessage {
    public ChatRequestRoomList(final ByteBuffer buffer) {
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    }
}