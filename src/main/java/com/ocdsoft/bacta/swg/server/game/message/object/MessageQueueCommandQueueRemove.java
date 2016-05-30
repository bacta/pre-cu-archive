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
@GameControllerMessage(GameControllerMessageType.COMMAND_QUEUE_REMOVE)
public final class MessageQueueCommandQueueRemove implements MessageQueueData {
    private final int sequenceId;
    private final float waitTime;
    private final int status;
    private final int statusDetail;

    public MessageQueueCommandQueueRemove(final ByteBuffer buffer) {
        sequenceId = buffer.getInt();
        waitTime = buffer.getFloat();
        status = buffer.getInt();
        statusDetail = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, sequenceId);
        BufferUtil.put(buffer, waitTime);
        BufferUtil.put(buffer, status);
        BufferUtil.put(buffer, statusDetail);
    }
}
