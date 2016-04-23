package com.ocdsoft.bacta.swg.precu.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class ConnectionServerLagResponse extends GameNetworkMessage {

    private static final short priority = 0x1;
    private static final int messageType = SOECRC32.hashCode(ConnectionServerLagResponse.class.getSimpleName());


    public ConnectionServerLagResponse() {
		super(priority, messageType);

		
	}

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }
}
