package com.ocdsoft.bacta.swg.precu.message.scene;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;

public class SceneObjectDestroyMessage extends SwgMessage {

	public SceneObjectDestroyMessage(SceneObject scno) {
		super(0x03, 0x4D45D504);
		
		writeLong(scno.getNetworkId());  // ObjectID
		writeByte(0);
	}
	
}
