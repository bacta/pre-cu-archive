package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/30/2016.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.NPC_CONVERSATION_STOP)
public final class MessageQueueStopNpcConversation implements MessageQueueData {
    private final long npc;
    private final StringId finalMessageId;
    private final String finalMessageProse; //unicode
    private final String finalResponse; //unicode;

    public MessageQueueStopNpcConversation(final ByteBuffer buffer) {
        npc = buffer.getLong();
        finalMessageId = new StringId(buffer);
        finalMessageProse = BufferUtil.getUnicode(buffer);
        finalResponse = BufferUtil.getUnicode(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, npc);
        BufferUtil.put(buffer, finalMessageId);
        BufferUtil.putUnicode(buffer, finalMessageProse);
        BufferUtil.putUnicode(buffer, finalResponse);
    }
}
