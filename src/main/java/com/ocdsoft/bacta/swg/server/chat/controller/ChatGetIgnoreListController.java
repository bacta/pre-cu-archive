package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatGetIgnoreList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@MessageHandled(handles = ChatGetIgnoreList.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(value = ConnectionRole.WHITELISTED)
public class ChatGetIgnoreListController implements GameNetworkMessageController<ChatGetIgnoreList> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatGetIgnoreListController.class);

    private final SwgChatServer chatServer;

    @Inject
    public ChatGetIgnoreListController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatGetIgnoreList message) throws Exception {
        this.chatServer.getIgnoreList(message.getCharacterId());
    }
}