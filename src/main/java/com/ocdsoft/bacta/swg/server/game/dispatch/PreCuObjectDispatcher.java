package com.ocdsoft.bacta.swg.server.game.dispatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ObjController;
import com.ocdsoft.bacta.soe.dispatch.ClasspathControllerLoader;
import com.ocdsoft.bacta.soe.dispatch.ControllerData;
import com.ocdsoft.bacta.soe.dispatch.ObjectDispatcher;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import gnu.trove.map.TIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public final class PreCuObjectDispatcher implements ObjectDispatcher<ObjControllerMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCuObjectDispatcher.class);

    private final TIntObjectMap<ControllerData> controllers;
    private ObjectService<ServerObject> objectService;

    @Inject
    public PreCuObjectDispatcher(final ClasspathControllerLoader controllerLoader,
                                 final ObjectService<ServerObject> objectService) {
        this.objectService = objectService;

        controllers = controllerLoader.getControllers(ObjController.class);
    }

    @Override
    public void dispatch(final SoeUdpConnection connection, final ObjControllerMessage message) {

        ControllerData<ObjController> controllerData = controllers.get(message.getMessageType());
        if (controllerData != null) {

            ObjController controller = controllerData.getController();
            final TangibleObject invoker = objectService.get(message.getReceiver());

            try {

                // Compare to ping tick on client? For disconnects?
                //int tickCount = message.getTickCount();

                LOGGER.trace("Routing to {}", controller.getClass().getSimpleName());
                controller.handleIncoming(connection, message, invoker);

            } catch (Exception e) {
                LOGGER.error("SOE Routing {}", message.getMessageType(), e);
            }
        }
    }
}
