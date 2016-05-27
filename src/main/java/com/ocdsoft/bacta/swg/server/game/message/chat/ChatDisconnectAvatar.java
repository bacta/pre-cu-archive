package com.ocdsoft.bacta.swg.server.game.message.chat;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/21/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatDisconnectAvatar extends GameNetworkMessage {
    private final long characterId;

    public ChatDisconnectAvatar(final ByteBuffer buffer) {
        characterId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(characterId);
    }
}