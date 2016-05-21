package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 * <p>
 * This is a GameServer->Client message that tells the client if the ChatServer is online or offline.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatServerStatus extends GameNetworkMessage {
    private final boolean status;

    public ChatServerStatus(final ByteBuffer buffer) {
        status = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, status);
    }
}