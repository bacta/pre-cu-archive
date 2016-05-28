package com.ocdsoft.bacta.swg.server.game.dispatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.script.ScriptEngine;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.CommandController;
import com.ocdsoft.bacta.soe.dispatch.ClasspathControllerLoader;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.soe.dispatch.ControllerData;
import com.ocdsoft.bacta.soe.message.CommandMessage;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import gnu.trove.map.TIntObjectMap;
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

    private final TIntObjectMap<ControllerData> controllers;
    private final ObjectService<ServerObject> objectService;
    private final ScriptEngine scriptEngine;
    private final Binding binding;

    @Inject
    public PreCuCommandDispatcher(final ClasspathControllerLoader controllerLoader,
                                  final ObjectService<ServerObject> objectService,
                                  final ScriptEngine scriptEngine) {

        this.objectService = objectService;
        this.scriptEngine = scriptEngine;

        this.binding = new Binding();

        controllers = controllerLoader.getControllers(CommandController.class);
    }

    @Override
    public void dispatchCommand(final SoeUdpConnection connection, final CommandMessage message, final TangibleObject invoker) {

        final ControllerData<CommandController> controllerData = controllers.get(message.getCommandHash());
        if (controllerData != null) {

            final CommandController controller = controllerData.getController();

            try {
                long targetId = message.getTargetId();
                TangibleObject target = null;

                if (targetId != 0) {
                    target = objectService.get(targetId);
                }

                controller.handleCommand(connection, invoker, target, message.getParams());

            } catch (Exception e) {
                LOGGER.error("SOE Routing", e);
            }
        }
    }
}
