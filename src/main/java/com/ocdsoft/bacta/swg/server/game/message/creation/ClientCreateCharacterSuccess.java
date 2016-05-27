package com.ocdsoft.bacta.swg.server.game.message.creation;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 struct __cppobj ClientCreateCharacterSuccess : GameNetworkMessage
 {
    Archive::AutoVariable<NetworkId> m_networkId;
 }; 
 */
@AllArgsConstructor
@Priority(0x2)
public final class ClientCreateCharacterSuccess extends GameNetworkMessage {

    private final long networkId;

    public ClientCreateCharacterSuccess(final ByteBuffer buffer) {
        networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(networkId);
    }
}
