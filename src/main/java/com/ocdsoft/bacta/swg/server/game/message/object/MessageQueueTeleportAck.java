package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/30/2016.
 * <p>
 * A teleport ack message is sent from a client to a game server to
 * acknowledge receipt of a teleport message.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.TELEPORT_ACK)
public final class MessageQueueTeleportAck implements MessageQueueData {
    private final int sequenceId;

    public MessageQueueTeleportAck(final ByteBuffer buffer) {
        sequenceId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, sequenceId);
    }
}
