package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

@NoArgsConstructor
public final class GameServerLagResponse extends GameNetworkMessage {

    static {
        priority = 0x4;
        messageType = SOECRC32.hashCode(GameServerLagResponse.class.getSimpleName());
    }

    public GameServerLagResponse(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }


}
