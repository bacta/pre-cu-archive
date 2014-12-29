package com.ocdsoft.bacta.swg.precu.router;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.message.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.network.swg.router.SwgMessageRouter;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.controller.object.ObjectController;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.annotations.ObjController;
import com.ocdsoft.bacta.swg.shared.util.ObjectControllerNames;
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

@ControllerScan(target = "com.ocdsoft.bacta")
@SwgController(server = ServerType.GAME, handles = ObjControllerMessage.class)
public class ObjectControllerRouter implements SwgMessageController<GameClient>, SwgMessageRouter<GameClient> {

    private VelocityEngine ve = null;

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private TIntObjectMap<ObjectController> controllers = new TIntObjectHashMap<ObjectController>();

    private ObjectService<SceneObject> objectService;

    private Injector injector;

    @Inject
    public ObjectControllerRouter(Injector injector, ObjectService<SceneObject> objectService) {
        this.objectService = objectService;
        this.injector = injector;
        loadControllers();
    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        int unknown = message.readInt();
        int type = message.readInt();


        routeMessage(type, client, message);
    }

    @Override
    public void routeMessage(int opcode, GameClient client, SoeByteBuf message) {
        ObjectController controller = controllers.get(opcode);

        if (controller == null) {

            handleMissingController(opcode, message);

            logger.error("Unhandled ObjectController: '" + ObjectControllerNames.get(opcode) + "' 0x" + Integer.toHexString(opcode));
            logger.error(SoeMessageUtil.bytesToHex(message));
            return;
        }

        try {

            long networkId = message.readLong();

            // Compare to ping tick on client? For disconnects?
            int tickCount = message.readInt();
            TangibleObject invoker = objectService.get(networkId);

            logger.trace("Routing to " + controller.getClass().getSimpleName());
            controller.handleIncoming(client, message, invoker);

        } catch (Exception e) {
            logger.error("SOE Routing", e);
        }
    }

    private void handleMissingController(int opcode, ByteBuf message) {

        writeTemplates(opcode, message);

        logger.error("Unhandled ObjectController: '" + ObjectControllerNames.get(opcode) + "' 0x" + Integer.toHexString(opcode));
        logger.error(SoeMessageUtil.bytesToHex(message));
    }

    private void loadControllers() {

        ControllerScan scanAnnotiation = getClass().getAnnotation(ControllerScan.class);

        if (scanAnnotiation == null) {
            logger.error("Missing @ControllerScan annotation, unable to load controllers");
            return;
        }

        Reflections reflections = new Reflections(scanAnnotiation.target());

        Set<Class<? extends ObjectController>> subTypes = reflections.getSubTypesOf(ObjectController.class);

        Iterator<Class<? extends ObjectController>> iter = subTypes.iterator();
        while (iter.hasNext()) {

            try {
                Class<? extends ObjectController> controllerClass = iter.next();

                ObjController controllerAnnotiation = controllerClass.getAnnotation(ObjController.class);

                if (controllerAnnotiation == null) {
                    logger.info("Missing @ObjController annotation, discarding: " + controllerClass.getName());
                    continue;
                }

                ObjectController controller = injector.getInstance(controllerClass);

                int id = controllerAnnotiation.id();

                if (!controllers.containsKey(id)) {
//					logger.debug("Adding object controller: " + ObjectControllerNames.get(id));

                    controllers.put(id, controller);
                }
            } catch (Exception e) {
                logger.error("Unable to add controller", e);
            }
        }
    }

    private void writeTemplates(int opcode, ByteBuf message) {

        initializeTemplating();

        String controllerName = ObjectControllerNames.get(opcode);

        if (controllerName.isEmpty() || controllerName.equalsIgnoreCase("unknown")) {
            logger.error("Unknown message opcode: 0x" + Integer.toHexString(opcode));
            return;
        }

        try {
            writeController(controllerName, opcode);
        } catch (Exception e) {
            logger.error("Unable to write controller", e);
        }

    }

    private void writeController(String controllerName, int opcode) throws Exception {

        String className = controllerName + "ObjController";

        Template t = ve.getTemplate("swg/src/main/resources/templates/objectcontroller.vm");

        VelocityContext context = new VelocityContext();

        context.put("packageName", "com.ocdsoft.bacta.swg.server.game.controller.object");
        context.put("controllerid", ("0x" + Integer.toHexString(opcode)));
        context.put("className", className);
        
        /* lets render a template */

        String outFileName = System.getProperty("user.dir") + "/swg/src/main/java/com/ocdsoft/bacta/swg/server/game/controller/object/" + className + ".java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileName)));

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
                ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, logger);
                ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");


                ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
                ve.init();
            }
        }
    }
}
