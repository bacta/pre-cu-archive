package com.ocdsoft.bacta.swg.server.game.message.chat;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/21/2016.
 * <p>
 * Server->Server communication. The Game Server tells the Chat Server to connect the avatar.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatConnectAvatar extends GameNetworkMessage {
    private final long characterId;
    private final String characterName;
    private final int stationId;
    private final boolean secure;
    private final boolean subscribed;

    public ChatConnectAvatar(final ByteBuffer buffer) {
        characterId = buffer.getLong();
        characterName = BufferUtil.getAscii(buffer);
        stationId = buffer.getInt();
        secure = BufferUtil.getBoolean(buffer);
        subscribed = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, characterId);
        BufferUtil.put(buffer, characterName);
        BufferUtil.put(buffer, stationId);
        BufferUtil.put(buffer, secure);
        BufferUtil.put(buffer, subscribed);
    }
}