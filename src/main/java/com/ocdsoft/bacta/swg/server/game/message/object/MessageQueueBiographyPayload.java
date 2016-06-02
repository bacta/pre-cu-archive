package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.biography.BiographyPayload;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 6/1/2016.
 */
@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.BIOGRAPHY_RETRIEVED)
public final class MessageQueueBiographyPayload implements MessageQueueData {
    private final BiographyPayload biographyPayload;

    public MessageQueueBiographyPayload(final ByteBuffer buffer) {
        biographyPayload = new BiographyPayload(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, biographyPayload);
    }
}
