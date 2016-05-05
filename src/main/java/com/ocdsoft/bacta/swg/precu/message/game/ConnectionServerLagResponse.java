package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
@Priority(0x1)
public final class ConnectionServerLagResponse extends GameNetworkMessage {

    public ConnectionServerLagResponse(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        
    }


}
