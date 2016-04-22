package com.ocdsoft.bacta.swg.precu.dispatch;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.soe.controller.ObjControllerId;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.precu.factory.ObjControllerMessageFactory;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.util.ObjectControllerNames;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
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

public class PreCuObjectDispatcher implements ObjectDispatcher<ObjControllerMessage> {

    private VelocityEngine ve;

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuObjectDispatcher.class);

    private TIntObjectMap<ControllerData> controllers = new TIntObjectHashMap<ControllerData>();

    private ObjectService<SceneObject> objectService;

    private final ObjControllerMessageFactory objControllerMessageFactory;

    private Injector injector;

    @Inject
    public PreCuObjectDispatcher(Injector injector, ObjectService<SceneObject> objectService, final ObjControllerMessageFactory objControllerMessageFactory) {
        this.objectService = objectService;
        this.injector = injector;
        this.objControllerMessageFactory = objControllerMessageFactory;
        loadControllers();
    }

    @Override
    public void dispatch(SoeUdpConnection client, ObjControllerMessage message) {

        ControllerData controllerData = controllers.get(message.getMessageType());

        if (controllerData == null) {

            ObjController controller = controllerData.getObjController();
            handleMissingController(message.getMessageType(), message);

            LOGGER.error("Unhandled ObjController: '" + ObjectControllerNames.get(message.getMessageType()) + "' 0x" + Integer.toHexString(message.getMessageType()));
            LOGGER.error(SoeMessageUtil.bytesToHex(message));
            return;
        }

        try {

            ObjController controller = controllerData.getObjController();
            TangibleObject invoker = objectService.get(message.getReceiver());

            // Compare to ping tick on client? For disconnects?
            //int tickCount = message.getTickCount();

            ObjControllerMessage objMessage = objControllerMessageFactory.create(controllerData.getClazz(), message);

            LOGGER.trace("Routing to " + controller.getClass().getSimpleName());
            controller.handleIncoming(client, objMessage, invoker);

        } catch (Exception e) {
            LOGGER.error("SOE Routing", e);
        }
    }

    private void handleMissingController(int opcode, ObjControllerMessage message) {

        writeTemplates(opcode, message);

        LOGGER.error("Unhandled ObjectControllerController: '" + ObjectControllerNames.get(opcode) + "' 0x" + Integer.toHexString(opcode));
        LOGGER.error(SoeMessageUtil.bytesToHex(message));
    }

    private void loadControllers() {

        Reflections reflections = new Reflections();

        Set<Class<? extends ObjController>> subTypes = reflections.getSubTypesOf(ObjController.class);

        Iterator<Class<? extends ObjController>> iter = subTypes.iterator();
        while (iter.hasNext()) {

            try {
                Class<? extends ObjController> controllerClass = iter.next();

                ObjControllerId controllerAnnotiation = controllerClass.getAnnotation(ObjControllerId.class);

                if (controllerAnnotiation == null) {
                    LOGGER.info("Missing @ObjControllerId annotation, discarding: " + controllerClass.getName());
                    continue;
                }

                ObjController controller = injector.getInstance(controllerClass);

                int id = controllerAnnotiation.id();
                if (!controllers.containsKey(id)) {

                    Class<? extends ObjControllerMessage> handledMessageClass = (Class<? extends ObjControllerMessage>) controllerAnnotiation.handles();
                    if(handledMessageClass != null) {
                        int hash = SOECRC32.hashCode(handledMessageClass.getSimpleName());
                        ControllerData newControllerData = new ControllerData(controller, handledMessageClass);

//					LOGGER.debug("Adding object controller: " + ObjectControllerNames.get(id));
                        controllers.put(id, newControllerData);
                    } else {
                        LOGGER.warn("Message does not extend ObjControllerMessage, not adding");
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Unable to add controller", e);
            }
        }
    }

    private void writeTemplates(int opcode, ObjControllerMessage message) {

        initializeTemplating();

        String controllerName = ObjectControllerNames.get(opcode);

        if (controllerName.isEmpty() || controllerName.equalsIgnoreCase("unknown")) {
            LOGGER.error("Unknown message opcode: 0x" + Integer.toHexString(opcode));
            return;
        }

        try {
            writeController(controllerName, opcode);
        } catch (Exception e) {
            LOGGER.error("Unable to write controller", e);
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
                ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, LOGGER);
                ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");


                ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
                ve.init();
            }
        }
    }

    private class ControllerData {
        @Getter
        private final ObjController objController;

        @Getter
        private final Class<? extends ObjControllerMessage> clazz;


        public ControllerData(final ObjController objController,
                              final Class<? extends ObjControllerMessage> clazz) {
            this.objController = objController;
            this.clazz = clazz;
        }
    }
}
