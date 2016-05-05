package com.ocdsoft.bacta.swg.precu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 struct __cppobj ClientCreateCharacterSuccess : GameNetworkMessage
 {
    Archive::AutoVariable<NetworkId> m_networkId;
 }; 
 */
@AllArgsConstructor
public final class ClientCreateCharacterSuccess extends GameNetworkMessage {

    static {
        priority = 0x2;
        messageType = SOECRC32.hashCode(ClientCreateCharacterSuccess.class.getSimpleName());
    }

    private final long networkId;

    public ClientCreateCharacterSuccess(final ByteBuffer buffer) {
        networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(networkId);
    }
}
