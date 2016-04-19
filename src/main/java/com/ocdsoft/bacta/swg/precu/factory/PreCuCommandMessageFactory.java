package com.ocdsoft.bacta.swg.precu.factory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.factory.ObjControllerMessageFactory;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.message.object.command.CommandMessage;

/**
 * Created by kyle on 4/10/2016.
 */
public class PreCuCommandMessageFactory implements ObjControllerMessageFactory<CommandMessage> {

    private final Injector injector;

    @Inject
    public PreCuCommandMessageFactory(final Injector injector) {
        this.injector = injector;
    }

    @Override
    public CommandMessage create(Class<? extends CommandMessage> messageClass, CommandMessage parentMessage) {
        CommandMessage message = injector.getInstance(messageClass);
        message.readFromBuffer(parentMessage.getBuffer());
        return message;
    }
}
