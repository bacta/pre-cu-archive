package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.object.Transform;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.util.Quaternion;

import java.nio.ByteBuffer;

public class UpdateTransformMessage extends GameNetworkMessage {

    private long objectId;
    private int movementCounter;
    private byte posture;
    private Transform transform;

    public UpdateTransformMessage() {
        super(0x08, 0x1B24F808);
        objectId = 0;
        transform = new Transform();
    }

    public UpdateTransformMessage(TangibleObject object) {
        super(0x08, 0x1B24F808);
        this.objectId = object.getNetworkId();
        this.movementCounter = object.getMovementCounter();
        //TODO: Use posture value
        this.posture = 0;
        this.transform = object.getTransform();
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        //NetworkId networkId
        //short positionX
        //short positionY
        //short positionZ
        //long sequenceNumber
        //byte speed
        //byte yaw
        //byte lookAtYaw
        //byte useLookAtYaw

        buffer.putLong(objectId);

        buffer.putShort((short) (transform.getPosition().x * 4 + 0.5f));
        buffer.putShort((short) (transform.getPosition().z * 4 + 0.5f)); // Position Z
        buffer.putShort((short) (transform.getPosition().y * 4 + 0.5f));

        buffer.putInt(movementCounter);

        buffer.putInt(posture);  // Posture?

        buffer.put((byte) Quaternion.convertToHeading(transform.getOrientation()));  // Angle
    }
}
