package com.ocdsoft.bacta.swg.server.message.login;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

@Priority(0x2)
public class RequestExtendedClusterInfo extends GameNetworkMessage {
    public RequestExtendedClusterInfo(final ByteBuffer buffer) {
        final int val = buffer.getInt(); //This message has an int val, but it's always 0.
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(0);
    }
}
