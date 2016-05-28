package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.object.DataTransformWithParent;

@MessageHandled(handles = DataTransformWithParent.class)
public class DataTransformWithParentObjController implements ObjController<DataTransformWithParent, TangibleObject> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataTransformWithParentObjController.class);
	
	@Override
	public void handleIncoming(final SoeUdpConnection connection, final DataTransformWithParent message, final TangibleObject invoker) {

		LOGGER.warn("This object controller is not implemented");
		
	}
}
