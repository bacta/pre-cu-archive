package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaByteStream;

import java.nio.ByteBuffer;

public final class BaselinesMessage extends GameNetworkMessage {

    private final long objectId;
    private final int opcode;
    private final AutoDeltaByteStream stream;
    private final byte packageId;

    public BaselinesMessage(ServerObject object, AutoDeltaByteStream stream, int packageId) {
        super(0x05, 0x68A75F0C);

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
        stream.pack(buffer);

        buffer.putInt(offset, buffer.position() - offset);
    }

    /*public void finish() {
        setInt(19, writerIndex() - 23);
    }*/
}
