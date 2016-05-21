package com.ocdsoft.bacta.swg.shared.chat.messages;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 5/20/2016.
 */
@Getter
@Priority(0x05)
@AllArgsConstructor
public final class ChatPutAvatarInRoom extends GameNetworkMessage {
    private final String avatarName;
    private final String roomName;
    private final boolean forceCreate;
    private final boolean createPrivate;

    public ChatPutAvatarInRoom(final ByteBuffer buffer) {
        avatarName = BufferUtil.getAscii(buffer);
        roomName = BufferUtil.getAscii(buffer);
        forceCreate = BufferUtil.getBoolean(buffer);
        createPrivate = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.put(buffer, avatarName);
        BufferUtil.put(buffer, roomName);
        BufferUtil.put(buffer, forceCreate);
        BufferUtil.put(buffer, createPrivate);
    }
}