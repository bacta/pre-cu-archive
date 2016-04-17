package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;

import java.nio.ByteBuffer;

public final class DeltasMessage extends GameNetworkMessage {

    private final long objectId;
    private final int opcode;
    private final AutoDeltaByteStream stream;
    private final byte packageId;

    public DeltasMessage(SceneObject object, AutoDeltaByteStream stream, int packageId) {
        super(0x05, 0x12862153);

        this.objectId = object.getNetworkId();
        this.opcode = object.getOpcode();
        this.stream = stream;
        this.packageId = (byte) packageId;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(objectId);
        buffer.putInt(opcode);
        buffer.put(packageId);

        int offset = buffer.position();

        buffer.putInt(0);
        stream.packDeltas(buffer);

        buffer.putInt(offset, buffer.position() - offset);
    }
}
