package com.ocdsoft.bacta.swg.shared.dispatch;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.server.game.controller.object.MessageQueueController;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by crush on 5/29/2016.
 */
@Singleton
public final class MessageQueueDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueueDispatcher.class);

    private final Injector injector;
    private final ServerObjectService serverObjectService;
    private final Map<GameControllerMessageType, MessageQueueController> controllers;

    @Inject
    public MessageQueueDispatcher(final Injector injector,
                                  final ServerObjectService serverObjectService) {
        this.injector = injector;
        this.serverObjectService = serverObjectService;
        this.controllers = new HashMap<>();

        loadControllers();
    }

    private void loadControllers() {
        final Reflections reflections = new Reflections();
        final Set<Class<? extends MessageQueueController>> subTypes = reflections.getSubTypesOf(MessageQueueController.class);
        subTypes.forEach(this::loadController);
    }

    private void loadController(final Class<? extends MessageQueueController> controllerClass) {
        final GameControllerMessage controllerAnnotation = controllerClass.getAnnotation(GameControllerMessage.class);
        final MessageQueueController controller = injector.getInstance(controllerClass);

        for (final GameControllerMessageType type : controllerAnnotation.value()) {

            if (controllers.containsKey(type)) {
                LOGGER.error("Controller {} is already handling type {}. Cannot load controller {} to also handle this type.",
                        controllers.get(type).getClass().getName(),
                        type,
                        controllerClass.getName());
            } else {
                LOGGER.debug("Loaded controller {} to handle type {}.",
                        controllerClass.getName(),
                        type);
                controllers.put(type, controller);
            }
        }
    }

    public void dispatch(final SoeUdpConnection connection, final ObjControllerMessage message) {
        final ServerObject actor = serverObjectService.get(message.getActorNetworkId());
        final GameControllerMessageType type = GameControllerMessageType.from(message.getMessageType());

        final MessageQueueController controller = controllers.get(type);

        if (controller != null) {
            controller.handleIncoming(connection, actor, message.getFlags(), message.getValue(), message.getData());
        } else {
            LOGGER.error("No controller loaded to handle ObjControllerMessage of type {}.", type);
        }
    }
}
