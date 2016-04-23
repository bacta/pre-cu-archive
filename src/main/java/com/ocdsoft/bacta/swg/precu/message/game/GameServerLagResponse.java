package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class GameServerLagResponse extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(GameServerLagResponse.class.getSimpleName());

    public GameServerLagResponse() {
		super(priority, messageType);

	}

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }
}
