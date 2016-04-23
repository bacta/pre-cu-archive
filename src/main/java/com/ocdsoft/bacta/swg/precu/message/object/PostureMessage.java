package com.ocdsoft.bacta.swg.precu.message.object;

import com.ocdsoft.bacta.swg.precu.object.ServerObject;

import java.nio.ByteBuffer;

/**
 * Created by Kyle on 3/24/14.
 */
public class PostureMessage extends ObjControllerMessage {

    private byte postureId;

    public PostureMessage(ServerObject scno, byte postureId) {
        super(0x1B, 0x131, scno.getNetworkId(), 0);


    }


    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.put(postureId);
        buffer.put((byte) 0);
    }
}
