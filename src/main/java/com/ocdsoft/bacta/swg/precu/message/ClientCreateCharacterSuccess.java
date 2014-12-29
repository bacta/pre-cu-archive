package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;

public class ClientCreateCharacterSuccess extends SwgMessage {
	 
	public ClientCreateCharacterSuccess(SceneObject player) {
		super(0x2, 0x1db575cc);
		
		writeLong(player.getNetworkId());
	}

}
