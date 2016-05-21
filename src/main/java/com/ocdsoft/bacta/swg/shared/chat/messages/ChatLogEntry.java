package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@AllArgsConstructor
public final class ChatLogEntry implements ByteBufferWritable {
    private final String from; //unicode
    private final String to; //unicode
    private final String channel; //unicode
    private final String message; //unicode
    private final long timestamp;

    public ChatLogEntry(final ByteBuffer buffer) {
        from = BufferUtil.getUnicode(buffer);
        to = BufferUtil.getUnicode(buffer);
        channel = BufferUtil.getUnicode(buffer);
        message = BufferUtil.getUnicode(buffer);
        timestamp = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putUnicode(buffer, from);
        BufferUtil.putUnicode(buffer, to);
        BufferUtil.putUnicode(buffer, channel);
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.put(buffer, timestamp);
    }
}
