package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.city.CityService;
import com.ocdsoft.bacta.swg.server.game.group.GroupService;
import com.ocdsoft.bacta.swg.server.game.guild.GuildService;
import com.ocdsoft.bacta.swg.shared.chat.messages.ChatOnCreateRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This message is ChatServer->GameServer.
 * <p>
 * The GameServer is notified that a room has been created. It then forwards it on
 * to other services to take further action.
 */
@MessageHandled(handles = ChatOnCreateRoom.class)
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
public final class ChatOnCreateRoomController implements GameNetworkMessageController<ChatOnCreateRoom> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatOnCreateRoomController.class);

    private final GuildService guildService;
    private final GroupService groupService;
    private final CityService cityService;

    @Inject
    public ChatOnCreateRoomController(final GuildService guildService,
                                      final GroupService groupService,
                                      final CityService cityService) {
        this.guildService = guildService;
        this.groupService = groupService;
        this.cityService = cityService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatOnCreateRoom message) {
        //We only do something if the room is a group, guild, or city room.
        guildService.notifyChatRoomCreated(message.getRoomData().getPath());
        groupService.notifyChatRoomCreated(message.getRoomData().getPath());
        cityService.notifyChatRoomCreated(message.getRoomData().getPath());
    }
}

