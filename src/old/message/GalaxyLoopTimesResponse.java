package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class GalaxyLoopTimesResponse extends GameNetworkMessage {
    public GalaxyLoopTimesResponse(long currentFrameMilliseconds, long lastFrameMilliseconds) {
        super(0x03, 0x4E428088);

        writeLong(currentFrameMilliseconds);
        writeLong(lastFrameMilliseconds);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
