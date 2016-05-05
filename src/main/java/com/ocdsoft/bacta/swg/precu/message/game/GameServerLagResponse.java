package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

@NoArgsConstructor
@Priority(0x4)
public final class GameServerLagResponse extends GameNetworkMessage {

    public GameServerLagResponse(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

    }


}
