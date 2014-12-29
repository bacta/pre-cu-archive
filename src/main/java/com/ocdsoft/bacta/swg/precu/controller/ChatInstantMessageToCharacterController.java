package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatInstantMessageToCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = ChatInstantMessageToCharacter.class)
public class ChatInstantMessageToCharacterController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public ChatInstantMessageToCharacterController() {

    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        ChatAvatarId recipient = new ChatAvatarId(message);

        String msg = message.readUnicode();
        int stringIdParameter = message.readInt();
        int sequence = message.readInt();

        ChatServerAgent agent = client.getChatServerAgent();

        if (agent != null)
            agent.sendInstantMessage(recipient, msg, sequence); //TODO: Stringidparams
    }
}
