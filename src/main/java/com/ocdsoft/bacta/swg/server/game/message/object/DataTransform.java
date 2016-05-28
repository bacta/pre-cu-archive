package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.soe.dispatch.MessageId;
import com.ocdsoft.bacta.soe.message.ObjControllerMessage;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@MessageId(0x71)
public class DataTransform extends ObjControllerMessage {

    private final int movementCounter;
    private final Transform transform;

    public DataTransform(final ByteBuffer buffer) {
        super(buffer);

        movementCounter = buffer.getInt();
        transform = new Transform(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        super.writeToBuffer(buffer);

        buffer.putInt(movementCounter);
        transform.writeToBuffer(buffer);
    }
    /**
         21 00 00 00 71 00 00 00 E3 01 00 00 01 00 00 00
    00 00 00 00 2A 08 00 00 01 00 00 00 00 00 00 00
    00 00 00 00 00 00 00 00 00 00 80 3F 00 00 C0 C5
    7A 57 8A 43 00 00 00 44 00 00 00 00 00 00 00 00
    00 

     */
}
