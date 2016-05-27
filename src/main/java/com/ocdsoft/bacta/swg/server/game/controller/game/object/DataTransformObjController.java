package com.ocdsoft.bacta.swg.server.game.controller.game.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.swg.server.game.message.game.object.DataTransform;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(id = 0x71, handles = DataTransform.class)
public class DataTransformObjController implements ObjController<DataTransform, TangibleObject> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataTransformObjController.class);
	
	@Override
	public void handleIncoming(final SoeUdpConnection connection, final DataTransform message, final TangibleObject invoker) {

		invoker.setMovementCounter(message.getMovementCounter());
		invoker.setTransformObjectToParent(message.getTransform());
	}
}
