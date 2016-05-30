package com.ocdsoft.bacta.swg.shared.dispatch;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueCommandQueueEnqueue;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by crush on 5/30/2016.
 * <p>
 * Dispatches commands to their respective controllers.
 */
@Singleton
public final class CommandQueueDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueueDispatcher.class);

    private final Injector injector;
    private final ServerObjectService serverObjectService;
    private final TIntObjectMap<CommandQueueController> controllers;

    @Inject
    public CommandQueueDispatcher(final Injector injector,
                                  final ServerObjectService serverObjectService) {
        this.injector = injector;
        this.serverObjectService = serverObjectService;
        this.controllers = new TIntObjectHashMap<>();

        loadControllers();
    }

    private void loadControllers() {
        final Reflections reflections = new Reflections();
        final Set<Class<? extends CommandQueueController>> subTypes = reflections.getSubTypesOf(CommandQueueController.class);
        subTypes.forEach(this::loadController);
    }

    private void loadController(final Class<? extends CommandQueueController> controllerClass) {
        final QueuesCommand controllerAnnotation = controllerClass.getAnnotation(QueuesCommand.class);

        if (controllerAnnotation != null) {
            final CommandQueueController controller = injector.getInstance(controllerClass);
            final int commandHash = SOECRC32.hashCode(controllerAnnotation.value());

            if (controllers.containsKey(commandHash)) {
                LOGGER.error("Controller {} is already handling command '{}'(0x{}). Cannot load controller {} to also handle this type.",
                        controllers.get(commandHash).getClass().getName(),
                        controllerAnnotation.value(),
                        Integer.toHexString(commandHash),
                        controllerClass.getName());
            } else {
                LOGGER.debug("Loaded controller {} to handle type '{}'(0x{}).",
                        controllerClass.getName(),
                        controllerAnnotation.value(),
                        Integer.toHexString(commandHash));

                controllers.put(commandHash, controller);
            }
        } else {
            LOGGER.error("Missing QueuesCommand annotation on {}", controllerClass.getName());
        }
    }

    public void dispatch(final SoeUdpConnection connection, final ServerObject actor, final MessageQueueCommandQueueEnqueue data) {
        final CommandQueueController controller = controllers.get(data.getCommandHash());

        //TODO: We need to do something with sequence Id...for example, actually queue the commands.

        if (controller != null) {
            final ServerObject target = serverObjectService.get(data.getTargetId());
            controller.handleCommand(connection, actor, target, data.getParams());
        } else {
            LOGGER.error("No controller loaded to handle CommandQueueController for command 0x{}.",
                    Integer.toHexString(data.getCommandHash()));
        }
    }
}
