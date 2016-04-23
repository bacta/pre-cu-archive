package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.utility.Transform;

import java.nio.ByteBuffer;

public final class SceneCreateObjectByCrc extends GameNetworkMessage {

    private long networkId;
    private Transform transform;
    private int crc;

    // This byte has to do with hyper space
    private byte hyperspace;

    public SceneCreateObjectByCrc() {
        super(0x05, 0xFE89DDEA);

        networkId = -1;
        transform = new Transform();
        crc = 0;
        hyperspace = 0;
    }

    public SceneCreateObjectByCrc(ServerObject scno) {
        super(0x05, 0xFE89DDEA);

        networkId = scno.getNetworkId();
        transform = scno.getTransform();
        crc = scno.getObjectTemplate().getCrcName().getCrc();
        hyperspace = 0;
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        //NetworkId networkId
        //Transform transform
        //int templateCrc
        //bool hyperspace

        buffer.putLong(networkId);  // NetworkID
        transform.writeToBuffer(buffer);
        buffer.putInt(crc); // Client ObjectCRC
        buffer.put(hyperspace);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        networkId = buffer.getLong();
        transform.readFromBuffer(buffer);
        crc = buffer.getInt();
        hyperspace = buffer.get();
    }

}
