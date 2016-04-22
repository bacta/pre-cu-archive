package com.ocdsoft.bacta.swg.precu.dispatch;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.script.ScriptEngine;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.CommandController;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.swg.precu.message.object.command.CommandMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import groovy.lang.Binding;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a controller and a dispatch
 * It handles all ObjectControllerController messages with the ID of 0x116
 * and routes the messages to the command implementation
 *
 * @author kyle
 */

@Singleton
public class PreCuCommandDispatcher implements CommandDispatcher<CommandMessage, TangibleObject> {

    private VelocityEngine ve = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuCommandDispatcher.class);

    private final TIntObjectMap<CommandController> controllers = new TIntObjectHashMap<CommandController>();

    private final Injector injector;

    private final ObjectService<SceneObject> objectService;

    private final ScriptEngine scriptEngine;

    private final Binding binding;

    @Inject
    public PreCuCommandDispatcher(final Injector injector,
                                  final ObjectService<SceneObject> objectService,
                                  final ScriptEngine scriptEngine) {

        this.injector = injector;
        this.objectService = objectService;
        this.scriptEngine = scriptEngine;

        this.binding = new Binding();

        loadBindings();
        loadControllers();
    }

    @Override
    public void dispatchCommand(int commandType, SoeUdpConnection connection, CommandMessage message, TangibleObject invoker) {



        /*CommandController controller = controllers.get(opcode);

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
        }*/
    }

    /*private void handleMissingController(int opcode, ByteBuffer message) {

        writeTemplates(opcode, message);

        String propertyName = Integer.toHexString(opcode).toUpperCase();

        logger.error("Unhandled Command: '" + CommandNames.get(propertyName)
                + "' 0x" + propertyName);
        logger.error(SoeMessageUtil.bytesToHex(message));
    }*/

    private void loadBindings() {

    }

    private void loadControllers() {
        loadScriptedControllers();
        loadStaticControllers();
    }

    private void loadScriptedControllers() {

    }

    private void loadStaticControllers() {

        /*ControllerScan scanAnnotiation = getClass().getAnnotation(
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
        }*/
    }
    /*
    private void writeTemplates(int opcode, ByteBuf message) {

        initializeTemplating();

        String commandName = CommandNames.get(opcode);

        if (commandName.isEmpty() || commandName.equalsIgnoreCase("unknown")) {
            logger.error("UNKNOWN command opcode: " + commandName + " 0x"
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

        Template t = ve.getTemplate("swg/src/main/resources/templates/CommandController.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", "com.ocdsoft.bacta.swg.server.game.controller.object.command");
        context.put("className", className);
        context.put("controllerid", controllerid);

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
    }*/
}
