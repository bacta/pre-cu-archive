package com.ocdsoft.bacta.swg.precu.controller.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.ObjController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjController(id = 0x71)
public class DataTransformObjController implements ObjectController {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public DataTransformObjController() {

    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message, TangibleObject invoker) {

        int movementCounter = message.readInt();

        invoker.setMovementCounter(movementCounter);

        float newRotX = message.readFloat();
        float newRotY = message.readFloat();
        float newRotZ = message.readFloat();
        float newRotW = message.readFloat();

        float newPosX = message.readFloat();
        float newPosZ = message.readFloat();

        float newPosY = message.readFloat();

        float newSpeed = message.readFloat();

        invoker.setOrientation(newRotX, newRotY, newRotZ, newRotW);
        invoker.setPosition(newPosX, newPosZ, newPosY);
    }
}
