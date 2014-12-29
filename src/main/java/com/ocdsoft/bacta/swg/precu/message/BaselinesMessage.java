package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.archive.delta.AutoDeltaByteStream;

public final class BaselinesMessage extends SwgMessage {
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
}
