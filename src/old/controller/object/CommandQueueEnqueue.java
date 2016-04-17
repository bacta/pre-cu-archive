package com.ocdsoft.bacta.swg.precu.dispatch;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ObjControllerHandled;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.util.CommandNames;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is a controller and a dispatch
 * It handles all ObjectControllerController messages with the ID of 0x116
 * and routes the messages to the command implementation
 *
 * @author kyle
 */

@ObjControllerHandled()
public class CommandQueueEnqueue implements ObjectController {

    private VelocityEngine ve = null;

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Inject
    public CommandQueueEnqueue(Injector injector, ObjectService<SceneObject> objectService) {
        this.injector = injector;
        this.objectService = objectService;
        loadControllers();
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message, TangibleObject invoker) {

        //sequenceId
        //commandHash
        //NetworkID targetId
        //UnicodeString params

        // Increase object / client counter
        int actionCounter = message.readInt();
        int commandCrc = message.readInt();

        float timer = 6;  //TODO: Command times lookup
        int error = 0;    //TODO: State errors
        int action = 0;

        routeCommand(commandCrc, client, message, invoker);

//        CommandQueueRemove remove = new CommandQueueRemove(
//                invoker.getNetworkId(),
//                actionCounter,
//                timer,
//                error,
//                action);
//        client.sendMessage(remove);
    }
}
