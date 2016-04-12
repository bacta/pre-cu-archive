package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;

public class SceneObjectDestroyMessage extends GameNetworkMessage {

	public SceneObjectDestroyMessage(SceneObject scno) {
		super(0x03, 0x4D45D504);
		
		writeLong(scno.getNetworkId());  // ObjectID
		writeByte(0);
	}
	
}
