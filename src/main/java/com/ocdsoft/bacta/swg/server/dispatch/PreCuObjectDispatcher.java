package com.ocdsoft.bacta.swg.server.dispatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.soe.dispatch.ClasspathControllerLoader;
import com.ocdsoft.bacta.soe.dispatch.ControllerData;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.soe.util.GameNetworkMessageTemplateWriter;
import com.ocdsoft.bacta.swg.server.message.game.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.shared.util.ObjectControllerNames;
import gnu.trove.map.TIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public final class PreCuObjectDispatcher implements ObjectDispatcher<ObjControllerMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuObjectDispatcher.class);

    private final GameNetworkMessageTemplateWriter templateWriter;
    private ObjectService<ServerObject> objectService;
    private final TIntObjectMap<ControllerData> controllers;


    @Inject
    public PreCuObjectDispatcher(final ClasspathControllerLoader<ObjController> controllerLoader,
                                 final GameNetworkMessageTemplateWriter templateWriter,
                                 final ObjectService<ServerObject> objectService) {

        this.objectService = objectService;
        this.templateWriter = templateWriter;
        this.controllers = controllerLoader.getControllers(ObjController.class);
    }

    @Override
    public void dispatch(SoeUdpConnection connection, ObjControllerMessage message) {

        ControllerData<ObjController> controllerData = controllers.get(message.getMessageType());

        if (controllerData != null) {

            try {

                ObjController controller = controllerData.getController();

                TangibleObject invoker = objectService.get(message.getReceiver());

                // Compare to ping tick on client? For disconnects?
                //int tickCount = message.getTickCount();

                LOGGER.trace("Routing to {}", controller.getClass().getSimpleName());
                controller.handleIncoming(connection, message, invoker);

            } catch (Exception e) {
                LOGGER.error("SOE Routing {}", message.getMessageType(), e);
            }

        } else {

//            templateWriter.createObjFiles(message.getMessageType(), message.getBuffer());

            LOGGER.error("Unhandled ObjController: '" + ObjectControllerNames.get(message.getMessageType()) + "' 0x" + Integer.toHexString(message.getMessageType()));
//            LOGGER.error(SoeMessageUtil.bytesToHex(message.getBuffer()));
            return;
        }
    }
}
