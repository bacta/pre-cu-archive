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
public final class ChatSystemMessage extends GameNetworkMessage {
    private final byte flags;
    private final String message; //unicode
    private final String outOfBand; //unicode

    public ChatSystemMessage(final ByteBuffer buffer) {
        this.flags = buffer.get();
        this.message = BufferUtil.getUnicode(buffer);
        this.outOfBand = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, flags);
        BufferUtil.putUnicode(buffer, message);
        BufferUtil.putUnicode(buffer, outOfBand);
    }

    public static final class Flags {
        public static final byte PERSONAL = 0x00;
        public static final byte BROADCAST = 0x01;
        public static final byte CHAT_BOX_ONLY = 0x02;
        public static final byte QUEST = 0x04;
    }
}