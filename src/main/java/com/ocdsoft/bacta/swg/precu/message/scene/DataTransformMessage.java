package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;

public class DataTransformMessage extends ObjControllerMessage {

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
}
