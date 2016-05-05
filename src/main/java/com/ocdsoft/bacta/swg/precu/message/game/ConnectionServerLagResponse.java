package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public final class ConnectionServerLagResponse extends GameNetworkMessage {

    static {
        priority = 0x1;
        messageType = SOECRC32.hashCode(ConnectionServerLagResponse.class.getSimpleName());
    }

    public ConnectionServerLagResponse(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        
    }


}
