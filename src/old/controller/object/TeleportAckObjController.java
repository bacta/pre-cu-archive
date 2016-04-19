package com.ocdsoft.bacta.swg.precu.controller.game.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id = 0x13f)
public class TeleportAckObjController implements ObjectController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message, TangibleObject invoker) {

        logger.warn("This object controller is not implemented");

    }
}
