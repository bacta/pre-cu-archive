package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/19/2016.
 */
@AllArgsConstructor
public class ConnectPlayerResponseMessage extends GameNetworkMessage {

    private final int result;

    public ConnectPlayerResponseMessage(final ByteBuffer buffer) {
        result = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(result);
    }
}
