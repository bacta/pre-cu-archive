package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.shared.dispatch.MessageQueueDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
@MessageHandled(handles = ObjControllerMessage.class, type = ServerType.GAME)
public final class ObjControllerMessageController implements GameNetworkMessageController<ObjControllerMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjControllerMessageController.class);

    private final MessageQueueDispatcher messageQueueDispatcher;

    @Inject
    public ObjControllerMessageController(final MessageQueueDispatcher messageQueueDispatcher) {
        this.messageQueueDispatcher = messageQueueDispatcher;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ObjControllerMessage message) {
        messageQueueDispatcher.dispatch(connection, message);
    }
}