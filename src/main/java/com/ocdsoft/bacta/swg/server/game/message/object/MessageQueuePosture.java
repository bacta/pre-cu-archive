package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/29/2016.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.SET_POSTURE)
public final class MessageQueuePosture implements MessageQueueData {
    private final byte posture;
    private final boolean isClientImmediate;

    public MessageQueuePosture(final ByteBuffer buffer) {
        posture = buffer.get();
        isClientImmediate = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, posture);
        BufferUtil.put(buffer, isClientImmediate);
    }
}
