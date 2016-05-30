package com.ocdsoft.bacta.swg.shared.serialize;

import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;

/**
 * Created by crush on 5/29/2016.
 */
public class MessageQueueDataTypeNotFoundException extends RuntimeException {
    public MessageQueueDataTypeNotFoundException(final GameControllerMessageType type) {
        super("Unable to create MessageQueueData for GameControllerMessageType: 0x" + Integer.toHexString(type.value));
    }
}
