package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.utility.Transform;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
public final class SceneCreateObjectByCrc extends GameNetworkMessage {

    static {
        priority = 0x5;
        messageType = SOECRC32.hashCode(SceneCreateObjectByCrc.class.getSimpleName()); // 0xFE89DDEA
    }

    private final long networkId;
    private final Transform transform;
    private final int crc;

    // This byte has to do with hyper space
    private final byte hyperspace;

    public SceneCreateObjectByCrc(ServerObject scno) {

        networkId = scno.getNetworkId();
        transform = scno.getTransform();
        crc = scno.getObjectTemplate().getCrcName().getCrc();
        hyperspace = 0;
    }

    public SceneCreateObjectByCrc(final ByteBuffer buffer) {
        networkId = buffer.getLong();
        transform = new Transform(buffer);
        crc = buffer.getInt();
        hyperspace = buffer.get();
    }
    @Override
    public void writeToBuffer(final ByteBuffer buffer) {

        //NetworkId networkId
        //Transform transform
        //int templateCrc
        //bool hyperspace

        buffer.putLong(networkId);  // NetworkID
        transform.writeToBuffer(buffer);
        buffer.putInt(crc); // Client ObjectCRC
        buffer.put(hyperspace);
    }


}
