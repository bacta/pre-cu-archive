package com.ocdsoft.bacta.swg.server.game.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Priority(0x5)
public final class SceneCreateObjectByCrc extends GameNetworkMessage {

    private final long networkId;
    private final Transform transform;
    private final int crc;

    // This byte has to do with hyper space
    private final byte hyperspace;

    public SceneCreateObjectByCrc(ServerObject scno) {

        networkId = scno.getNetworkId();
        transform = scno.getTransformObjectToParent();
        crc = scno.getSharedTemplate().getCrcName().getCrc();
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
