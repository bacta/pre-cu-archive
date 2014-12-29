package com.ocdsoft.bacta.swg.precu.controller.object;

import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.ObjController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id=0xf5)
public class MissionListRequestObjController implements ObjectController {

	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	
	@Override
	public void handleIncoming(GameClient client, SoeByteBuf message, TangibleObject invoker) {

		logger.warn("This object controller is not implemented");
		
	}
}
