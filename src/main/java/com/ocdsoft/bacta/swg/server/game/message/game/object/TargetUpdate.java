package com.ocdsoft.bacta.swg.server.game.message.game.object;

import java.nio.ByteBuffer;

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
