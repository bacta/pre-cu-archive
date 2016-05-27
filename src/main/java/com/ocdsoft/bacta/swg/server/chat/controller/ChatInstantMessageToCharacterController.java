package com.ocdsoft.bacta.swg.server.chat.controller;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.GameClientMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatInstantMessageToCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = ChatInstantMessageToCharacter.class)
public class ChatInstantMessageToCharacterController implements GameClientMessageController<ChatInstantMessageToCharacter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatInstantMessageToCharacterController.class);

    @Override
    public void handleIncoming(long[] distributionList, boolean reliable, SoeUdpConnection connection, ChatInstantMessageToCharacter message) throws Exception {
        LOGGER.info("Handling incoming intended for {} with message {}",
                message.getCharacterName().getFullName(),
                message.getMessage());

        LOGGER.warn("Not implemented");
    }
}
