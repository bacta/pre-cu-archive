package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.shared.object.ConversationStarter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/30/2016.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.NPC_CONVERSATION_START)
public final class MessageQueueStartNpcConversation implements MessageQueueData {
    private final long npc;
    private final ConversationStarter starter;
    private final String conversationName;
    private final int appearanceOverrideTemplateCrc;

    public MessageQueueStartNpcConversation(final ByteBuffer buffer) {
        this.npc = buffer.getLong();
        this.starter = ConversationStarter.from(buffer.get());
        this.conversationName = BufferUtil.getAscii(buffer);
        this.appearanceOverrideTemplateCrc = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, npc);
        BufferUtil.put(buffer, starter.value);
        BufferUtil.putAscii(buffer, conversationName);
        BufferUtil.put(buffer, appearanceOverrideTemplateCrc);

    }
}
