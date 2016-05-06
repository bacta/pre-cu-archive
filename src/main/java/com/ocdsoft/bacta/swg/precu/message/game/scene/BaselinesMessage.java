package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;

import java.nio.ByteBuffer;

@Priority(0x5)
public final class BaselinesMessage extends GameNetworkMessage {

    private final long target;
    private final int typeId;
    private final ByteBuffer packageBuffer;
    private final byte packageId;

    public BaselinesMessage(final ServerObject object, final AutoDeltaByteStream sourcePackage, final int packageId) {
        this.target = object.getNetworkId();
        this.typeId = object.getObjectType();
        this.packageId = (byte) packageId;

        this.packageBuffer = ByteBuffer.allocate(4096);
        sourcePackage.pack(this.packageBuffer);
    }

    // TODO: Constructor Deserialization
    public BaselinesMessage(final ByteBuffer buffer) {
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

    /*public void finish() {
        setInt(19, writerIndex() - 23);
    }*/
}
