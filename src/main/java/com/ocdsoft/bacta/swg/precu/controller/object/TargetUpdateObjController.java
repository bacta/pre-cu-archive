package com.ocdsoft.bacta.swg.precu.controller.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.shared.annotations.ObjController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id = 0x126)
public class TargetUpdateObjController implements ObjectController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public TargetUpdateObjController() {

    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message, TangibleObject invoker) {

        if (invoker instanceof CreatureObject) {
            CreatureObject creatureObject = (CreatureObject) invoker;
            creatureObject.setLookAtTarget(message.readLong());
        }

    }
}
