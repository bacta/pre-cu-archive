package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;

import java.nio.ByteBuffer;

public final class DeltasMessage extends GameNetworkMessage {

    static {
        priority = 0x5;
        messageType = SOECRC32.hashCode(DeltasMessage.class.getSimpleName()); // 0x12862153
    }

    private final long objectId;
    private final int opcode;
    private final AutoDeltaByteStream stream;
    private final byte packageId;

    public DeltasMessage(final ServerObject object, final AutoDeltaByteStream stream, final int packageId) {

        this.objectId = object.getNetworkId();
        this.opcode = object.getOpcode();
        this.stream = stream;
        this.packageId = (byte) packageId;
    }

    // TODO: Implement this
    public DeltasMessage(final ByteBuffer buffer) {
        this.objectId = 0;
        this.opcode = 0;
        this.stream = null;
        this.packageId = 0;
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(objectId);
        buffer.putInt(opcode);
        buffer.put(packageId);

        int offset = buffer.position();

        buffer.putInt(0);
        stream.packDeltas(buffer);

        buffer.putInt(offset, buffer.position() - offset);
    }
}
