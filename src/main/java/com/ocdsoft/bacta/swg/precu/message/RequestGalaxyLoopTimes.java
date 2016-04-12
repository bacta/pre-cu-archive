package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class RequestGalaxyLoopTimes extends GameNetworkMessage {
    public RequestGalaxyLoopTimes() {
        super(0x1, 0x7d842d68);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
