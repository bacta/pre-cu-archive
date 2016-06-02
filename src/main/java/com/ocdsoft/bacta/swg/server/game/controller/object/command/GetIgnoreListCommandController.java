package com.ocdsoft.bacta.swg.server.game.controller.object.command;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.controller.object.CommandQueueController;
import com.ocdsoft.bacta.swg.server.game.controller.object.QueuesCommand;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 */
@QueuesCommand("getIgnoreList")
public final class GetIgnoreListCommandController implements CommandQueueController {
    private static Logger LOGGER = LoggerFactory.getLogger(GetIgnoreListCommandController.class);

    private final GameChatService chatService;

    @Inject
    public GetIgnoreListCommandController(final GameChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void handleCommand(final SoeUdpConnection connection, final ServerObject actor, final ServerObject target, final String params) {
        final String player = chatService.constructChatAvatarId(actor).getFullName();
        this.chatService.getIgnoreList(player);
    }
}