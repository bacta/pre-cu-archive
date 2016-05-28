package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.swg.server.game.message.object.TargetUpdate;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = TargetUpdate.class)
public class TargetUpdateObjController implements ObjController<TargetUpdate, TangibleObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetUpdateObjController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final TargetUpdate message, final TangibleObject invoker) {

        LOGGER.warn("This object controller is not implemented");

    }
}
