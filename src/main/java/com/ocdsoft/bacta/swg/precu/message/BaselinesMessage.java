package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;

import java.nio.ByteBuffer;

public final class BaselinesMessage extends GameNetworkMessage {
    public BaselinesMessage(SceneObject object, AutoDeltaByteStream stream, int packageId) {
        super(0x05, 0x68A75F0C);

        writeLong(object.getNetworkId());
        writeInt(object.getOpcode());
        writeByte(packageId);
        writeInt(0);

        int offset = writerIndex();

        stream.pack(this);

        setInt(offset - 4, writerIndex() - offset);
    }

    public BaselinesMessage(SceneObject object, int index) {
        super(0x05, 0x68A75F0C);

        //this.index = index;
        //type = object.getOpcode();


        writeLong(object.getNetworkId());
        writeInt(object.getOpcode());
        writeByte(index);
        writeInt(0);
    }

    public void finish() {
        setInt(19, writerIndex() - 23);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
