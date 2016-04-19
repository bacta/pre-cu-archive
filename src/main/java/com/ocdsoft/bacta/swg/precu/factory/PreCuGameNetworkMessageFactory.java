package com.ocdsoft.bacta.swg.precu.factory;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.factory.GameNetworkMessageFactory;
import com.ocdsoft.bacta.soe.factory.ObjControllerMessageFactory;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/17/2016.
 */
public class PreCuGameNetworkMessageFactory implements GameNetworkMessageFactory {

    private final ObjControllerMessageFactory<ObjControllerMessage> objControllerMessageFactory;

    @Inject
    public PreCuGameNetworkMessageFactory(final ObjControllerMessageFactory<ObjControllerMessage> objControllerMessageFactory) {
        this.objControllerMessageFactory = objControllerMessageFactory;
    }

    @Override
    public GameNetworkMessage create(int gameMessageType, ByteBuffer buffer) {
        return null;
    }

}
