package com.ocdsoft.bacta.swg.precu.controller.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;

@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
@MessageHandled(handles = ObjControllerMessage.class, type = ServerType.GAME)
public final class ObjectControllerController implements GameNetworkMessageController<ObjControllerMessage> {

    private final ObjectDispatcher<ObjControllerMessage> objectDispatcher;

    @Inject
    public ObjectControllerController(final ObjectDispatcher<ObjControllerMessage> objectDispatcher) {
        this.objectDispatcher = objectDispatcher;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ObjControllerMessage message) {
        objectDispatcher.dispatch(connection, message);
    }

}
