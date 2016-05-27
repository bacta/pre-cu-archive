package com.ocdsoft.bacta.swg.server.controller.game.chat;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatServerConnectionOpened;

/**
 * Created by crush on 5/21/2016.
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatServerConnectionOpened.class)
public class ChatServerConnectionOpenedController implements GameNetworkMessageController<ChatServerConnectionOpened> {
    @Override
    public void handleIncoming(SoeUdpConnection connection, ChatServerConnectionOpened message) throws Exception {

    }
}
