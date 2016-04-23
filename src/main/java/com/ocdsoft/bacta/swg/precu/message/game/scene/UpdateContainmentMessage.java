package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;

import java.nio.ByteBuffer;

public class UpdateContainmentMessage extends GameNetworkMessage {

    private long objectId;
    private long containerId;
    private int slotArrangement;

    public UpdateContainmentMessage(ServerObject object) {
        super(0x04, 0x56CBDE9E);

        this.objectId = object.getNetworkId();
        this.containerId = object.getContainedBy();
        this.slotArrangement = object.getCurrentArrangement();
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.objectId = buffer.getLong();
        this.containerId = buffer.getLong();
        this.slotArrangement = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(objectId);
        buffer.putLong(containerId);
        buffer.putInt(slotArrangement);
    }
}
