package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;

import java.nio.ByteBuffer;

public final class DeltasMessage extends GameNetworkMessage {
    public DeltasMessage(SceneObject obj, AutoDeltaByteStream byteStream, int packageId) {
        super(0x05, 0x12862153);

        writeLong(obj.getNetworkId());
        writeInt(obj.getOpcode());
        writeByte(packageId);

        int sizeOffset = buffer.writerIndex();

        writeInt(0);

        byteStream.packDeltas(this);

        setInt(sizeOffset, buffer.writerIndex() - sizeOffset - 4);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
