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
public final class ChatInstantMessageToClient extends GameNetworkMessage {
    private final ChatAvatarId fromName;
    private final String message; //unicode
    private final String outOfBand; //unicode

    public ChatInstantMessageToClient(final ByteBuffer buffer) {
        this.fromName = new ChatAvatarId(buffer);
        this.message = BufferUtil.getUnicode(buffer);
        this.outOfBand = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, fromName);
        BufferUtil.put(buffer, message);
        BufferUtil.put(buffer, outOfBand);
    }
}