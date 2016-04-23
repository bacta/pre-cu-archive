package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;

import java.nio.ByteBuffer;

public class SceneObjectDestroyMessage extends GameNetworkMessage {

	private long objectId;
    private byte unknown;

	public SceneObjectDestroyMessage(ServerObject scno) {
		super(0x03, 0x4D45D504);

        this.objectId = scno.getNetworkId();
        this.unknown = 0;
	}

	@Override
	public void readFromBuffer(ByteBuffer buffer) {
        this.objectId = buffer.getLong();
        this.unknown = buffer.get();
	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(objectId);  // ObjectID
        buffer.put(unknown);
	}
}
