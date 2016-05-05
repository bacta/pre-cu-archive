package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.util.Quaternion;
import com.ocdsoft.bacta.swg.shared.utility.Transform;

import java.nio.ByteBuffer;

public final class UpdateTransformMessage extends GameNetworkMessage {

    static {
        priority = 0x08;
        messageType = SOECRC32.hashCode(UpdateContainmentMessage.class.getSimpleName()); // 0x1B24F808
    }

    private final long objectId;
    private final int movementCounter;
    private final byte posture;
    private final Transform transform;

    public UpdateTransformMessage(final TangibleObject object) {
        this.objectId = object.getNetworkId();
        this.movementCounter = object.getMovementCounter();
        //TODO: Use posture value
        this.posture = 0;
        this.transform = object.getTransform();
    }

    public UpdateTransformMessage(final ByteBuffer buffer) {
        this.objectId = buffer.getLong();
        this.movementCounter = buffer.getInt();
        //TODO: Use posture value
        this.posture = 0;
        this.transform = new Transform(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
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
