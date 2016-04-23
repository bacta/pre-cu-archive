package com.ocdsoft.bacta.swg.precu.controller.game.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.message.game.object.command.CommandMessage;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a controller and a dispatch
 * It handles all ObjectControllerController messages with the ID of 0x116
 * and routes the messages to the command implementation
 *
 * @author kyle
 */

@MessageHandled(id = 0x116, handles = ObjControllerMessage.class)
public class CommandQueueEnqueue implements ObjController<CommandMessage, TangibleObject> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandQueueEnqueue.class);

    private final CommandDispatcher<CommandMessage, TangibleObject> commandDispatcher;

    @Inject
    public CommandQueueEnqueue(final CommandDispatcher<CommandMessage, TangibleObject> commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, CommandMessage message, TangibleObject invoker) {

        //sequenceId
        //commandHash
        //NetworkID targetId
        //UnicodeString params

        // Increase object / client counter
        int sequenceId = message.getSequenceId();
        int commandHash = message.getCommandHash();

        float timer = 6;  //TODO: Command times lookup
        int error = 0;    //TODO: State errors
        int action = 0;

        commandDispatcher.dispatchCommand(connection, message, invoker);

//        CommandQueueRemove remove = new CommandQueueRemove(
//                invoker.getNetworkId(),
//                actionCounter,
//                timer,
//                error,
//                action);
//        client.sendMessage(remove);
    }
}
