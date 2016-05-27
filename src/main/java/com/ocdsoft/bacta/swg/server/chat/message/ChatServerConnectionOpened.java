package com.ocdsoft.bacta.swg.server.chat.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/21/2016.
 * <p>
 * Just tells the GameServer that it is now online basically.
 */
@Priority(0x05)
public final class ChatServerConnectionOpened extends GameNetworkMessage {
    public ChatServerConnectionOpened() {
    }

    public ChatServerConnectionOpened(final ByteBuffer buffer) {
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    }
}