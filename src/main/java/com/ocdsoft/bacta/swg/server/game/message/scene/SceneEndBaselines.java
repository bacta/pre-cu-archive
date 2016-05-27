package com.ocdsoft.bacta.swg.server.game.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Priority(0x2)
public final class SceneEndBaselines extends GameNetworkMessage {

	private final long objectId;

	public SceneEndBaselines(ByteBuffer buffer) {
        objectId = buffer.getLong();
	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(objectId);
	}
}
