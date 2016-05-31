package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.server.game.radialmenu.ObjectMenuRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.List;

@Getter
@AllArgsConstructor
@GameControllerMessage(GameControllerMessageType.OBJECT_MENU_REQUEST)
public final class MessageQueueObjectMenuRequest implements MessageQueueData {
    private final List<ObjectMenuRequestData> data;
    private final long requestorId;
    private final long targetId;

    public MessageQueueObjectMenuRequest(final ByteBuffer buffer) {
        data = BufferUtil.getArrayList(buffer, ObjectMenuRequestData::new);
        requestorId = buffer.getLong();
        targetId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, data);
        BufferUtil.put(buffer, requestorId);
        BufferUtil.put(buffer, targetId);
    }
}
