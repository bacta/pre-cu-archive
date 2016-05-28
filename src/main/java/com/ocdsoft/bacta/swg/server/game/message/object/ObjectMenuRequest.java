package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.soe.dispatch.MessageId;
import com.ocdsoft.bacta.soe.message.ObjControllerMessage;

import java.nio.ByteBuffer;

@MessageId(0x146)
public class ObjectMenuRequest extends ObjControllerMessage {

    public ObjectMenuRequest(final ByteBuffer buffer) {
        super(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         23 00 00 00 46 01 00 00 43 04 00 00 01 00 00 00
    00 00 00 00 97 32 91 00 00 00 00 00 43 04 00 00
    01 00 00 00 01 00 00 00 01 00 07 00 01 00 00 00
    00 01 

     */
}
