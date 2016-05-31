package com.ocdsoft.bacta.swg.server.game.controller.object.queue;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.server.game.controller.object.MessageQueueController;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueCommandQueueEnqueue;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.shared.dispatch.CommandQueueDispatcher;

/**
 * Created by crush on 5/30/2016.
 */
@GameControllerMessage(GameControllerMessageType.COMMAND_QUEUE_ENQUEUE)
public class CommandQueueEnqueueController implements MessageQueueController<MessageQueueCommandQueueEnqueue> {
    private final CommandQueueDispatcher dispatcher;

    @Inject
    public CommandQueueEnqueueController(final CommandQueueDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ServerObject actor, final int flags, final float value, final MessageQueueCommandQueueEnqueue data) {
        dispatcher.dispatch(connection, actor, data);
    }
}
