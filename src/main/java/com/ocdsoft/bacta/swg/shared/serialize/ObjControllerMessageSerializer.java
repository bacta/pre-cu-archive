package com.ocdsoft.bacta.swg.shared.serialize;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.controller.object.GameControllerMessage;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueData;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by crush on 5/29/2016.
 */
@Singleton
public final class ObjControllerMessageSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjControllerMessageSerializer.class);

    private final Map<GameControllerMessageType, Constructor<? extends MessageQueueData>> messageQueueDataConstructorMap;

    @Inject
    public ObjControllerMessageSerializer() {
        this.messageQueueDataConstructorMap = new HashMap<>();
        loadMessages();
    }

    private void loadMessages() {
        final Reflections reflections = new Reflections();
        final Set<Class<? extends MessageQueueData>> subTypes = reflections.getSubTypesOf(MessageQueueData.class);
        subTypes.forEach(this::loadMessageClass);
    }

    private void loadMessageClass(final Class<? extends MessageQueueData> messageClass) {
        final GameControllerMessage gameControllerMessage = messageClass.getAnnotation(GameControllerMessage.class);

        for (final GameControllerMessageType type : gameControllerMessage.value()) {
            if (messageQueueDataConstructorMap.containsKey(type)) {
                LOGGER.error("{} cannot handle GameControllerMessageType {} because it is already being handled by class {}.",
                        messageClass.getName(),
                        type,
                        messageQueueDataConstructorMap.get(type).getClass().getName());
            }

            try {
                final Constructor<? extends MessageQueueData> constructor = messageClass.getConstructor(ByteBuffer.class);

                LOGGER.debug("Setting {} to handle GameControllerMessageType {}.", messageClass.getName(), type);
                messageQueueDataConstructorMap.put(type, constructor);

            } catch (NoSuchMethodException e) {
                LOGGER.error("{} does not have a constructor taking ByteBuffer", messageClass.getName(), e);
            }
        }
    }

    public MessageQueueData createMessageQueueData(final ByteBuffer buffer, final GameControllerMessageType type) {
        final Constructor<? extends MessageQueueData> messageConstructor = messageQueueDataConstructorMap.get(type);

        if (messageConstructor == null)
            throw new MessageQueueDataTypeNotFoundException(type);

        try {
            return messageConstructor.newInstance(buffer);
        } catch (Exception e) {
            LOGGER.error("Unable to construct message {}", messageConstructor.getName(), e);
            return null;
        }
    }
}
