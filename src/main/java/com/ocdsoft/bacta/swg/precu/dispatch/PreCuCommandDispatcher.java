package com.ocdsoft.bacta.swg.precu.dispatch;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.script.ScriptEngine;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.CommandController;
import com.ocdsoft.bacta.soe.dispatch.ClasspathControllerLoader;
import com.ocdsoft.bacta.soe.dispatch.CommandDispatcher;
import com.ocdsoft.bacta.soe.dispatch.ControllerData;
import com.ocdsoft.bacta.soe.factory.GameNetworkMessageFactory;
import com.ocdsoft.bacta.soe.util.CommandNames;
import com.ocdsoft.bacta.soe.util.GameNetworkMessageTemplateWriter;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.precu.message.object.command.CommandMessage;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
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

    private final GameNetworkMessageTemplateWriter templateWriter;

    private final GameNetworkMessageFactory gameNetworkMessageFactory;

    private final ObjectService<ServerObject> objectService;

    private final ScriptEngine scriptEngine;

    private final Binding binding;

    @Inject
    public PreCuCommandDispatcher(final ClasspathControllerLoader<CommandController> controllerLoader,
                                  final GameNetworkMessageFactory gameNetworkMessageFactory,
                                  final GameNetworkMessageTemplateWriter templateWriter,
                                  final ObjectService<ServerObject> objectService,
                                  final ScriptEngine scriptEngine) {

        this.gameNetworkMessageFactory = gameNetworkMessageFactory;
        this.objectService = objectService;
        this.templateWriter = templateWriter;
        this.scriptEngine = scriptEngine;

        this.binding = new Binding();

        controllers = controllerLoader.getControllers(CommandController.class);
    }

    @Override
    public void dispatchCommand(SoeUdpConnection connection, CommandMessage message, TangibleObject invoker) {

        ControllerData<CommandController> controllerData = controllers.get(message.getCommandHash());

        if (controllerData != null) {

            CommandController controller = controllerData.getController();

            try {

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

            } catch (Exception e) {
                LOGGER.error("SOE Routing {}", message.getMessageType(), e);
            }

        } else {

            templateWriter.createCommandFiles(message.getCommandHash(), message.getBuffer());

            LOGGER.error("Unhandled Command: '" + CommandNames.get(message.getCommandHash()));
            LOGGER.error(SoeMessageUtil.bytesToHex(message.getBuffer()));
            return;
        }
    }


}
