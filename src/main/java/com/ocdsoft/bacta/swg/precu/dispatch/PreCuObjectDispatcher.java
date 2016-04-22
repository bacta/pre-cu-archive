package com.ocdsoft.bacta.swg.precu.dispatch;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.service.object.ObjectService;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.soe.controller.ObjControllerId;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.util.GameNetworkMessageTemplateWriter;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuObjectDispatcher.class);

    private TIntObjectMap<ControllerData> controllers = new TIntObjectHashMap<ControllerData>();

    private final GameNetworkMessageTemplateWriter templateWriter;

    private ObjectService<SceneObject> objectService;

    private Injector injector;

    @Inject
    public PreCuObjectDispatcher(Injector injector,
                                 final GameNetworkMessageTemplateWriter templateWriter,
                                 ObjectService<SceneObject> objectService) {

        this.objectService = objectService;
        this.templateWriter = templateWriter;
        this.injector = injector;
        loadControllers();
    }

    @Override
    public void dispatch(SoeUdpConnection client, ObjControllerMessage message) {

        ControllerData controllerData = controllers.get(message.getMessageType());

        if (controllerData != null) {

            try {

                ObjController controller = controllerData.getObjController();
                TangibleObject invoker = objectService.get(message.getReceiver());

                // Compare to ping tick on client? For disconnects?
                //int tickCount = message.getTickCount();

                LOGGER.trace("Routing to {}", controller.getClass().getSimpleName());
                controller.handleIncoming(client, message, invoker);

            } catch (Exception e) {
                LOGGER.error("SOE Routing {}", message.getMessageType(), e);
            }

        } else {

            templateWriter.createObjFiles(message.getMessageType(), message.getBuffer());

            LOGGER.error("Unhandled ObjController: '" + ObjectControllerNames.get(message.getMessageType()) + "' 0x" + Integer.toHexString(message.getMessageType()));
            LOGGER.error(SoeMessageUtil.bytesToHex(message));
            return;
        }
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
