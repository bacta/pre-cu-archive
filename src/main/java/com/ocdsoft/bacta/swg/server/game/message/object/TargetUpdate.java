package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.soe.dispatch.MessageId;
import com.ocdsoft.bacta.soe.message.ObjControllerMessage;

import java.nio.ByteBuffer;

@MessageId(0x126)
public class TargetUpdate extends ObjControllerMessage {

    public TargetUpdate(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }
    /**
     83 00 00 00 26 01 00 00 89 01 00 00 01 00 00 00
     00 00 00 00 39 B1 38 00 00 00 00 00

     */
}
