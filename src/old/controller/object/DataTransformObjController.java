package com.ocdsoft.bacta.swg.precu.controller.game.object;

import com.ocdsoft.bacta.soe.controller.ObjControllerId;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.game.scene.DataTransformMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

@ObjControllerId(id = 0x71, handles = DataTransformMessage.class)
public class DataTransformObjController implements ObjController<DataTransformMessage, TangibleObject> {

    @Override
    public void handleIncoming(SoeUdpConnection connection, DataTransformMessage dataTransformMessage, TangibleObject invoker) {
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
