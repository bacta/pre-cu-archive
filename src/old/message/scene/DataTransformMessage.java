package com.ocdsoft.bacta.swg.precu.message.game.scene;

import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.magnos.steer.vec.Vec3;

import javax.vecmath.Quat4f;
import java.nio.ByteBuffer;

public class DataTransformMessage extends ObjControllerMessage {

	private int movementCounter;
    private Quat4f orientation;
    private Vec3 position;

    public DataTransformMessage(ObjControllerMessage parent) {
        super(parent.getFlag(), parent.getMessageType(), parent.getReceiver(), parent.getTickCount());
    }

	public DataTransformMessage(TangibleObject object) {
		super(0x1b, 0x71, object.getNetworkId(), 0);
		
		writeInt(object.getMovementCounter());

		writeFloat(object.getOrientation().x);  // Direction X
		writeFloat(object.getOrientation().y);  // Direction Y
		writeFloat(object.getOrientation().z);  // Direction Z
		writeFloat(object.getOrientation().w);  // Direction W

		writeFloat(object.getPosition().x);
		writeFloat(object.getPosition().z); // Position Z
		writeFloat(object.getPosition().y);

		writeInt(0);
		
	}

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
