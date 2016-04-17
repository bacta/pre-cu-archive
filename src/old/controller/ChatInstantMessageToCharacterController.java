package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.object.chat.ChatAvatarId;
import com.ocdsoft.bacta.swg.precu.chat.ChatServerAgent;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatInstantMessageToCharacter;
import com.ocdsoft.bacta.swg.shared.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(ChatInstantMessageToCharacter.class)
public class ChatInstantMessageToCharacterController implements GameNetworkMessageController<ChatInstantMessageToCharacter> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, ChatInstantMessageToCharacter message) throws Exception {
        throw new NotImplementedException();
//        ChatAvatarId recipient = new ChatAvatarId(message);
//
//        String msg = message.readUnicode();
//        int stringIdParameter = message.readInt();
//        int sequence = message.readInt();
//
//        ChatServerAgent agent = client.getChatServerAgent();
//
//        if (agent != null) {
//            agent.sendInstantMessage(recipient, msg, sequence); //TODO: Stringidparams
//        }
    }
}
