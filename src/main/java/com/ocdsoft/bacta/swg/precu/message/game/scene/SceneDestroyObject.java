package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public final class SceneDestroyObject extends GameNetworkMessage {

	static {
		priority = 0x03;
		messageType = SOECRC32.hashCode(SceneDestroyObject.class.getSimpleName()); // 0x4D45D504
	}

	private final long objectId;
    private final boolean hyperspace;

	public SceneDestroyObject(final ByteBuffer buffer) {
        this.objectId = buffer.getLong();
        this.hyperspace = BufferUtil.getBoolean(buffer);
	}

	@Override
	public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(objectId);  // ObjectID
        BufferUtil.putBoolean(buffer, hyperspace);
	}
}
