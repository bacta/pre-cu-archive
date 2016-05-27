package com.ocdsoft.bacta.swg.server.chat.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/21/2016.
 * <p>
 * This message goes from the ChatServer to the GameServer, and tells the GameServer that the
 * avatar with the given characterId has connected.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatAvatarConnected extends GameNetworkMessage {
    private final long characterId;

    public ChatAvatarConnected(final ByteBuffer buffer) {
        characterId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterId);
    }
}