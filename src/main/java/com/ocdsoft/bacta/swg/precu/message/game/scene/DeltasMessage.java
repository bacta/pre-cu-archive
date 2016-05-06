package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;
import lombok.Getter;

import java.nio.ByteBuffer;

@Priority(0x5)
public final class DeltasMessage extends GameNetworkMessage {

    @Getter
    private final long target;
    @Getter
    private final int typeId;
    @Getter
    private final ByteBuffer packageBuffer;
    @Getter
    private final byte packageId;

    public DeltasMessage(final ServerObject object, final AutoDeltaByteStream stream, final int packageId) {
        this.target = object.getNetworkId();
        this.typeId = object.getObjectType();
        this.packageId = (byte) packageId;

        this.packageBuffer = ByteBuffer.allocate(4096); //Need to figure out a way to optimize this?
        stream.packDeltas(this.packageBuffer);
    }

    public DeltasMessage(final ByteBuffer buffer) {
        this.target = buffer.getLong();
        this.typeId = buffer.getInt();
        this.packageId = buffer.get();

        final int size = buffer.getInt();

        this.packageBuffer = ByteBuffer.allocate(size);
        this.packageBuffer.put(buffer.array(), buffer.position(), size);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(target);
        buffer.putInt(typeId);
        buffer.put(packageId);

        buffer.putInt(packageBuffer.position());
        buffer.put(packageBuffer.array(), 0, packageBuffer.position());
    }
}
