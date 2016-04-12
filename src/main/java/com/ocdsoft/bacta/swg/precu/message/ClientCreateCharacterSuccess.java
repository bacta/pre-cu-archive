package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;

import java.nio.ByteBuffer;

public class ClientCreateCharacterSuccess extends GameNetworkMessage {
	 
	public ClientCreateCharacterSuccess(SceneObject player) {
		super(0x2, 0x1db575cc);
		
		writeLong(player.getNetworkId());
	}

	@Override
	public void readFromBuffer(ByteBuffer buffer) {

	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {

	}
}
