package com.ocdsoft.bacta.swg.precu.controller.game.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.annotations.ObjController;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id = 0x126)
public class TargetUpdateObjController implements ObjectController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public TargetUpdateObjController() {

    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message, TangibleObject invoker) {

        if (invoker instanceof CreatureObject) {
            CreatureObject creatureObject = (CreatureObject) invoker;
            creatureObject.setLookAtTarget(message.readLong());
        }

    }
}
