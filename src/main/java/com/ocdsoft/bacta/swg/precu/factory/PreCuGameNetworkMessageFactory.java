package com.ocdsoft.bacta.swg.precu.factory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.soe.controller.Command;
import com.ocdsoft.bacta.soe.controller.ObjControllerId;
import com.ocdsoft.bacta.soe.factory.GameNetworkMessageFactory;
import com.ocdsoft.bacta.soe.factory.GameNetworkMessageTypeNotFoundException;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.message.object.command.CommandMessage;
import io.netty.util.collection.IntObjectHashMap;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/17/2016.
 */
public class PreCuGameNetworkMessageFactory implements GameNetworkMessageFactory {

    private final static int OBJECT_CONTROLLER_MESSAGE = SOECRC32.hashCode(ObjControllerMessage.class.getSimpleName());
    private final static int COMMAND_CONTROLLER_MESSAGE = SOECRC32.hashCode(CommandMessage.class.getSimpleName());

    private final Injector injector;

    private final IntObjectHashMap<Class<? extends GameNetworkMessage>> messageClassMap;

    @Inject
    public PreCuGameNetworkMessageFactory(final Injector injector) {
        this.injector = injector;
        this.messageClassMap = new IntObjectHashMap<>();
    }

    @Override
    public GameNetworkMessage create(Class<? extends GameNetworkMessage> messageClass) {
        return injector.getInstance(messageClass);
    }

    @Override
    public GameNetworkMessage createAndDeserialize(int gameMessageType, ByteBuffer buffer) throws NullPointerException {

        if ( gameMessageType == OBJECT_CONTROLLER_MESSAGE ) {

            gameMessageType = buffer.getInt(10);

            if ( gameMessageType == COMMAND_CONTROLLER_MESSAGE ) {
                gameMessageType = buffer.getInt(30);
            }
        }

        Class<? extends GameNetworkMessage> messageClass = messageClassMap.get(gameMessageType);

        GameNetworkMessage messageInstance = create(messageClass);
        if(messageInstance == null) {
            throw new GameNetworkMessageTypeNotFoundException(gameMessageType);
        }
        messageInstance.readFromBuffer(buffer);

        return messageInstance;
    }

    @Override
    public void addHandledMessageClass(int hash, Class<? extends GameNetworkMessage> handledMessageClass) {

        if(hash == OBJECT_CONTROLLER_MESSAGE) {

            Class<? extends ObjControllerMessage> handledObjMessageClass = (Class<? extends ObjControllerMessage>) handledMessageClass;
            ObjControllerId objAnnotation = handledObjMessageClass.getAnnotation(ObjControllerId.class);
            hash = objAnnotation.id();

            if (hash == COMMAND_CONTROLLER_MESSAGE) {

                Class<? extends CommandMessage> handledCommandMessageClass = (Class<? extends CommandMessage>) handledMessageClass;
                Command commandAnnotation = handledCommandMessageClass.getAnnotation(Command.class);
                hash = commandAnnotation.id();
            }
        }

        messageClassMap.put(hash, handledMessageClass);
    }
}
