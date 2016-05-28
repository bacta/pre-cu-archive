package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.soe.dispatch.MessageId;
import com.ocdsoft.bacta.soe.message.ObjControllerMessage;

import java.nio.ByteBuffer;

@MessageId(0xF1)
public class DataTransformWithParent extends ObjControllerMessage {

    public DataTransformWithParent(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 F1 00 00 00 43 04 00 00 01 00 00 00
    00 00 00 00 48 7A 00 00 E2 00 00 00 79 2A 13 00
    00 00 00 00 00 00 00 00 52 32 F7 BE 00 00 00 00
    AD 2F 60 3F AA 63 0B 42 F2 09 D7 3D 64 F0 AA C0
    9A 99 E9 40 00 00 00 00 00 

     */
}
