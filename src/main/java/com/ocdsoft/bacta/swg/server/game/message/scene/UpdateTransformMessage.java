package com.ocdsoft.bacta.swg.server.game.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.math.Transform;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import lombok.Getter;

import java.nio.ByteBuffer;

@Priority(0x8)
public final class UpdateTransformMessage extends GameNetworkMessage {
    @Getter
    private final long networkId;
    private final short positionX;
    private final short positionY;
    private final short positionZ;
    @Getter
    private final int sequenceNumber;
    @Getter
    private final byte speed;
    private final byte yaw;
    private final byte lookAtYaw;
    private final byte useLookAtYaw;

    public UpdateTransformMessage(final TangibleObject object,
                                  final int sequenceNumber,
                                  final Transform transform,
                                  final byte speed,
                                  final float lookAtYaw,
                                  final boolean useLookAtYaw) {
        this.networkId = object.getNetworkId();
        this.sequenceNumber = sequenceNumber;
        this.speed = speed;

        final Vector pos = transform.getPositionInParent();
        this.positionX = (short) (pos.x * 4);
        this.positionY = (short) (pos.y * 4);
        this.positionZ = (short) (pos.z * 4);

        this.yaw = (byte) (transform.getLocalFrameKInParentSpace().theta() * 16);
        this.lookAtYaw = (byte) (lookAtYaw * 16);

        this.useLookAtYaw = (useLookAtYaw ? (byte) 1 : (byte) 0);
    }

    public UpdateTransformMessage(final ByteBuffer buffer) {
        this.networkId = buffer.getLong();
        this.positionX = buffer.getShort();
        this.positionY = buffer.getShort();
        this.positionZ = buffer.getShort();
        this.sequenceNumber = buffer.getInt();
        this.speed = buffer.get();
        this.yaw = buffer.get();
        this.lookAtYaw = buffer.get();
        this.useLookAtYaw = buffer.get();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(networkId);
        buffer.putShort(positionX);
        buffer.putShort(positionY);
        buffer.putShort(positionZ);
        buffer.putInt(sequenceNumber);
        buffer.put(speed);
        buffer.put(yaw);
        buffer.put(lookAtYaw);
        buffer.put(useLookAtYaw);
    }

    public Transform getTransform() {
        final Transform transform = new Transform();
        final Vector pos = new Vector(
                (float) positionX / 4.f,
                (float) positionY / 4.f,
                (float) positionZ / 4.f);

        transform.setPositionInParentSpace(pos);
        transform.yaw((float) yaw / 16.f);
        transform.reorthonormalize();

        return transform;
    }

    public boolean getUseLookAtYaw() {
        return useLookAtYaw != 0;
    }

    public float getLookAtYaw() {
        return getUseLookAtYaw() ? (float) lookAtYaw / 16.f : 0.f;
    }
}
