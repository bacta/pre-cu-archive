package com.ocdsoft.bacta.swg.precu.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.swg.annotations.Command;
import com.ocdsoft.bacta.swg.annotations.ObjController;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.message.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.controller.object.ObjectController;
import com.ocdsoft.bacta.swg.server.game.controller.object.command.CommandController;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.util.CommandNames;
import com.ocdsoft.network.annotation.ControllerScan;
import com.ocdsoft.network.service.object.ObjectService;
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
 * This class is a controller and a router
 * It handles all ObjectController messages with the ID of 0x116
 * and routes the messages to the command implementation
 *
 * @author kyle
 */

@ControllerScan(target = "com.ocdsoft.bacta")
@ObjController(id = 0x116)
public class CommandQueueEnqueue implements ObjectController, CommandRouter {

    private VelocityEngine ve = null;

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private TIntObjectMap<CommandController> controllers = new TIntObjectHashMap<CommandController>();

    private Injector injector;

    private ObjectService<SceneObject> objectService;

    @Inject
    public CommandQueueEnqueue(Injector injector, ObjectService<SceneObject> objectService) {
        this.injector = injector;
        this.objectService = objectService;
        loadControllers();
    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message, TangibleObject invoker) {

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

    @Override
    public void routeCommand(int opcode, GameClient client, SoeByteBuf message, TangibleObject invoker) {

        CommandController controller = controllers.get(opcode);

        if (controller == null) {

            handleMissingController(opcode, message);

        } else {

            try {
                long targetId = message.readLong();
                TangibleObject target = null;

                if (targetId != 0) {
                    target = objectService.get(targetId);
                }

                String params = message.readUnicode();

                controller.handleCommand(client, invoker, target, params);

            } catch (Exception e) {
                logger.error("SOE Routing", e);
            }
        }
    }

    private void handleMissingController(int opcode, ByteBuf message) {

        writeTemplates(opcode, message);

        String propertyName = Integer.toHexString(opcode).toUpperCase();

        logger.error("Unhandled Command: '" + CommandNames.get(propertyName)
                + "' 0x" + propertyName);
        logger.error(SoeMessageUtil.bytesToHex(message));
    }

    private void loadControllers() {

        ControllerScan scanAnnotiation = getClass().getAnnotation(
                ControllerScan.class);

        if (scanAnnotiation == null) {
            logger.error("Missing @ControllerScan annotation, unable to load controllers");
            return;
        }

        Reflections reflections = new Reflections(scanAnnotiation.target());

        Set<Class<? extends CommandController>> subTypes = reflections
                .getSubTypesOf(CommandController.class);

        Iterator<Class<? extends CommandController>> iter = subTypes.iterator();
        while (iter.hasNext()) {

            try {
                Class<? extends CommandController> controllerClass = iter
                        .next();

                Command controllerAnnotiation = controllerClass
                        .getAnnotation(Command.class);

                if (controllerAnnotiation == null) {
                    logger.info("Missing @Command annotation, discarding: "
                            + controllerClass.getName());
                    continue;
                }

                CommandController controller = injector
                        .getInstance(controllerClass);

                int id = controllerAnnotiation.id();

                if (!controllers.containsKey(id)) {

                    // logger.debug("Adding command controller: " +
                    // CommandNames.get(id));

                    controllers.put(id, controller);
                }

            } catch (Exception e) {
                logger.error("Unable to add controller", e);
            }
        }
    }

    private void writeTemplates(int opcode, ByteBuf message) {

        initializeTemplating();

        String commandName = CommandNames.get(opcode);

        if (commandName.isEmpty() || commandName.equalsIgnoreCase("unknown")) {
            logger.error("Unknown command opcode: " + commandName + " 0x"
                    + Integer.toHexString(opcode));
            return;
        }

        try {
            writeController(commandName, ("0x" + Integer.toHexString(opcode)));
        } catch (Exception e) {
            logger.error("Unable to write controller", e);
        }

    }

    private void writeController(String commandName, String controllerid)
            throws Exception {

        String className = commandName + "CommandController";

        Template t = ve.getTemplate("swg/src/main/resources/templates/commandcontroller.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", "com.ocdsoft.bacta.swg.server.game.controller.object.command");
        context.put("className", className);
        context.put("controllerid", controllerid);

		/* lets render a template */

        String outFileName = System.getProperty("user.dir")
                + "/swg/src/main/java/com/ocdsoft/bacta/swg/server/game/controller/object/command/" + className + ".java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                outFileName)));

        if (!ve.evaluate(context, writer, t.getName(), "")) {
            throw new Exception("Failed to convert the template into class.");
        }

        t.merge(context, writer);

        writer.flush();
        writer.close();
    }

    private void initializeTemplating() {
        synchronized (controllers) {
            if (ve == null) {
                ve = new VelocityEngine();
                ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
                        logger);
                ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");

                //File file = new File(System.getProperty("user.dir") + "/templates/");

                //ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
                //		file.getAbsolutePath());
                //ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,
                //		"true");
                ve.init();
            }
        }
    }
}
