package com.ocdsoft.bacta.swg.server.chat.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.SwgChatServer;
import com.ocdsoft.bacta.swg.server.game.message.GameServerOnline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/27/2016.
 */
@MessageHandled(handles = GameServerOnline.class, type = ServerType.CHAT)
@ConnectionRolesAllowed(value = ConnectionRole.WHITELISTED)
public class GameServerOnlineController implements GameNetworkMessageController<GameServerOnline> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameServerOnlineController.class);

    private final SwgChatServer chatServer;

    @Inject
    public GameServerOnlineController(final SwgChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GameServerOnline message) throws Exception {
        LOGGER.info("Chat server notified that cluster {} is online.", message.getClusterServer().getName());
        chatServer.notifyGameServerOnline(connection);
    }
}