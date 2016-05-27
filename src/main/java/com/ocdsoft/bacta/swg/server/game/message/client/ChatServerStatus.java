package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 5/19/2016.
 */

@AllArgsConstructor
public class ChatServerStatus extends GameNetworkMessage {

    private final int unknown;

    public ChatServerStatus(final ByteBuffer buffer) {
        unknown = buffer.get();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.put((byte) unknown);
    }
}
