package com.ocdsoft.bacta.swg.shared.chat.messages;

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
public final class ChatAddFriend extends GameNetworkMessage {
    private final ChatAvatarId chatAvatarId;
    private final int sequence;

    public ChatAddFriend(final ByteBuffer buffer) {
        this.chatAvatarId = new ChatAvatarId(buffer);
        this.sequence = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        chatAvatarId.writeToBuffer(buffer);
        buffer.putInt(sequence);
    }
}
