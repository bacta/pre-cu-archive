package com.ocdsoft.bacta.swg.server.game.message.scene;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
@Priority(0x3)
public final class SceneDestroyObject extends GameNetworkMessage {

	private final long objectId;
    private final boolean hyperspace;

	public SceneDestroyObject(final ServerObject serverObject) {
		this.objectId = serverObject.getNetworkId();
		this.hyperspace = serverObject.getLocalFlag(ServerObject.LocalObjectFlags.HYPERSPACE_ON_DESTRUCT);
	}

	public SceneDestroyObject(final ByteBuffer buffer) {
        this.objectId = buffer.getLong();
        this.hyperspace = BufferUtil.getBoolean(buffer);
	}

	@Override
	public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(objectId);  // ObjectID
		BufferUtil.put(buffer, hyperspace);
	}
}
