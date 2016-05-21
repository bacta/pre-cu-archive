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
public final class ChatPersistentMessageToServer extends GameNetworkMessage {
    public final static int MAX_MESSAGE_SIZE = 4000;

    private final int sequence;
    private final ChatAvatarId to;
    private final String subject; //unicode
    private final String message; //unicode
    private final String outOfBand; //unicode

    public ChatPersistentMessageToServer(final ByteBuffer buffer) {
        this.message = BufferUtil.getUnicode(buffer);
        this.outOfBand = BufferUtil.getUnicode(buffer);
        this.sequence = buffer.getInt();
        this.subject = BufferUtil.getUnicode(buffer);
        this.to = new ChatAvatarId(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.putUnicode(buffer, outOfBand);
        BufferUtil.put(buffer, sequence);
        BufferUtil.putUnicode(buffer, subject);
        BufferUtil.put(buffer, to);
    }
}