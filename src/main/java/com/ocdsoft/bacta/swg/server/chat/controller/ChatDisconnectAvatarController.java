package com.ocdsoft.bacta.swg.server.chat.controller;

import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatDisconnectAvatar;

/**
 * Created by crush on 5/21/2016.
 */
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
@MessageHandled(handles = ChatDisconnectAvatar.class, type = ServerType.CHAT)
public class ChatDisconnectAvatarController implements GameNetworkMessageController<ChatDisconnectAvatar> {

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatDisconnectAvatar message) throws Exception {

    }
}
