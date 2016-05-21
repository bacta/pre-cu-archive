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
public final class ChatOnRequestLog extends GameNetworkMessage {
    private final int sequence;
    private final ChatLogEntry[] logEntries;

    public ChatOnRequestLog(final ByteBuffer buffer) {
        final int size = buffer.getInt();
        logEntries = new ChatLogEntry[size];

        for (int i = 0; i < size; ++i)
            logEntries[i] = new ChatLogEntry(buffer);

        sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        final int size = logEntries.length;
        BufferUtil.put(buffer, size);

        for (int i = 0; i < size; ++i)
            BufferUtil.put(buffer, logEntries[i]);

        BufferUtil.put(buffer, sequence);
    }
}