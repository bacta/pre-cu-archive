package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
public final class SceneEndBaselines extends GameNetworkMessage {

	static {
		priority = 0x2;
		messageType = SOECRC32.hashCode(SceneEndBaselines.class.getSimpleName()); // 0x2C436037
	}

	private final long objectId;

	public SceneEndBaselines(ByteBuffer buffer) {
        objectId = buffer.getLong();
	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(objectId);
	}
}
