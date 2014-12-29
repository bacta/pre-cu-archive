package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;

public class SceneCreateObjectByCrc extends SwgMessage {

    public SceneCreateObjectByCrc(SceneObject scno) {
        super(0x05, 0xFE89DDEA);

        //NetworkId networkId
        //Transform transform
        //int templateCrc
        //bool hyperspace

        writeLong(scno.getNetworkId());  // NetworkID
        writeFloat(scno.getOrientation().x); // X Direction
        writeFloat(scno.getOrientation().y); // Y Direction
        writeFloat(scno.getOrientation().z); // Z Direction
        writeFloat(scno.getOrientation().w); // W Direction
        writeFloat(scno.getPosition().x); // X Position
        writeFloat(scno.getPosition().z); // Z Position
        writeFloat(scno.getPosition().y); // Y Position
        writeInt(scno.getObjectTemplate().getCrcName());  // Client ObjectCRC
        writeByte(0);
    }
}
