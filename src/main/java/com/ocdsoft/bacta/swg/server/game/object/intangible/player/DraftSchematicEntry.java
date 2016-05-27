package com.ocdsoft.bacta.swg.server.game.object.intangible.player;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.Getter;

import java.nio.ByteBuffer;

public class DraftSchematicEntry implements ByteBufferWritable {
	@Getter private final int serverObjectCrc = 0;
	@Getter private final int clientObjectCrc = 0;

	public DraftSchematicEntry(final ByteBuffer buffer) {

	}

	@Override
	public void writeToBuffer(ByteBuffer buffer) {
		throw new UnsupportedOperationException("Not implemented");
	}
}