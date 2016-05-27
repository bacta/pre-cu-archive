package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/19/2016.
 */
@AllArgsConstructor
public class ParametersMessage extends GameNetworkMessage {

    private final int weatherUpdateInterval;

    public ParametersMessage(final ByteBuffer buffer) {
        weatherUpdateInterval = buffer.getInt();
    }


    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(weatherUpdateInterval);
    }
}
