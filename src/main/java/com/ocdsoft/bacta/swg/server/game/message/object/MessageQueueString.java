package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/30/2016.
 * <p>
 * A generic message queue data that contains a simple payload of one String.
 * The meaning of the String is context and message dependant.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage({
        GameControllerMessageType.ATTACH_SCRIPT,
        GameControllerMessageType.DETACH_SCRIPT,
        GameControllerMessageType.ANIMATION_ACTION
})
public final class MessageQueueString implements MessageQueueData {
    private final String string;

    public MessageQueueString(final ByteBuffer buffer) {
        string = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, string);
    }
}
