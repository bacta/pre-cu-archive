package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class HeartbeatMessage extends GameNetworkMessage {

	public HeartbeatMessage() {
		super(0x1, 0xA16CF9AF);
		
	}

	@Override
	public void readFromBuffer(ByteBuffer buffer) {

	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {

	}
}
