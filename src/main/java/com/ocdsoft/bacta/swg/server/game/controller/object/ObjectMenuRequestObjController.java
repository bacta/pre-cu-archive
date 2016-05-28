package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjectMenuRequest;
import com.ocdsoft.bacta.soe.controller.ObjController;

@MessageHandled(handles = ObjectMenuRequest.class)
public class ObjectMenuRequestObjController implements ObjController<ObjectMenuRequest, TangibleObject> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMenuRequestObjController.class);
	
	@Override
	public void handleIncoming(final SoeUdpConnection connection, final ObjectMenuRequest message, final TangibleObject invoker) {

		LOGGER.warn("This object controller is not implemented");
		
	}
}
