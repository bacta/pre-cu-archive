package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 6/1/2016.
 */
@Getter
@AllArgsConstructor
public final class ChatSpamLimited extends GameNetworkMessage {
    private final int timeUntilCanTalk;

    public ChatSpamLimited(final ByteBuffer buffer) {
        this.timeUntilCanTalk = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.put(buffer, timeUntilCanTalk);
    }
}
