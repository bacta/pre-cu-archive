package com.ocdsoft.bacta.swg.server.chat.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/28/2016.
 * <p>
 * Tells a GameServer that the ChatServer has come online. The GameServer is now able to talk to the ChatServer
 * and do its setup sequence.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatServerOnline extends GameNetworkMessage {
    private final String address;
    private final int port;

    public ChatServerOnline(final ByteBuffer buffer) {
        this.address = BufferUtil.getAscii(buffer);
        this.port = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, address);
        BufferUtil.put(buffer, port);
    }
}