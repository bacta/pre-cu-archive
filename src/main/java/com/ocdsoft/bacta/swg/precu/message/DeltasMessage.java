package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.archive.delta.AutoDeltaByteStream;

public final class DeltasMessage extends SwgMessage {
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
}
