package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/30/2016.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.OPPONENT_INFO)
public final class MessageQueueOpponentInfo implements MessageQueueData {
    private final long opponent;
    private final short health;
    private final short action;
    private final short mind;


    public MessageQueueOpponentInfo(final ByteBuffer buffer) {
        opponent = buffer.getLong();
        health = buffer.getShort();
        action = buffer.getShort();
        mind = buffer.getShort();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, opponent);
        BufferUtil.put(buffer, health);
        BufferUtil.put(buffer, action);
        BufferUtil.put(buffer, mind);
    }
}
