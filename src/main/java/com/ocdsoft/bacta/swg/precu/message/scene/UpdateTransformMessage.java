package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.util.Quaternion;

public class UpdateTransformMessage extends SwgMessage {

    public UpdateTransformMessage(TangibleObject object) {
        super(0x08, 0x1B24F808);

        //NetworkId networkId
        //short positionX
        //short positionY
        //short positionZ
        //long sequenceNumber
        //byte speed
        //byte yaw
        //byte lookAtYaw
        //byte useLookAtYaw

        writeLong(object.getNetworkId());

        writeShort((short) (object.getPosition().x * 4 + 0.5f));
        writeShort((short) (object.getPosition().z * 4 + 0.5f)); // Position Z
        writeShort((short) (object.getPosition().y * 4 + 0.5f));

        writeInt(object.getMovementCounter());

        writeByte(0);  // Posture?

        writeByte((byte) Quaternion.convertToHeading(object.getOrientation()));  // Angle
    }




}
