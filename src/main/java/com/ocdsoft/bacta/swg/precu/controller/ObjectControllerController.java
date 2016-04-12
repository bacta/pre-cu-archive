package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;

@GameNetworkMessageHandled(ObjControllerMessage.class)
public final class ObjectControllerController implements GameNetworkMessageController<ObjControllerMessage> {

    private final ObjectDispatcher<ObjControllerMessage> objectDispatcher;

    @Inject
    public ObjectControllerController(final ObjectDispatcher objectDispatcher) {
        this.objectDispatcher = objectDispatcher;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ObjControllerMessage message) {
        objectDispatcher.dispatch(connection, message);
    }

}
