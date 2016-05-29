package com.ocdsoft.bacta.swg.server.game.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.chat.message.ChatAvatarConnected;
import com.ocdsoft.bacta.swg.server.game.city.CityService;
import com.ocdsoft.bacta.swg.server.game.guild.GuildService;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.object.ServerObjectService;
import gnu.trove.list.TIntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/21/2016.
 * <p>
 * ChatServer->GameServer.
 * An avatar has connected. Notice that this does not send ChatOnAvatarConnected. That is sent from the ChatServer
 * as a GameClientMessage.
 */
@MessageHandled(handles = ChatAvatarConnected.class)
@ConnectionRolesAllowed({ConnectionRole.WHITELISTED})
public class ChatAvatarConnectedController implements GameNetworkMessageController<ChatAvatarConnected> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatAvatarConnectedController.class);

    private final ServerObjectService serverObjectService;
    private final GuildService guildService;
    private final CityService cityService;

    @Inject
    public ChatAvatarConnectedController(final ServerObjectService serverObjectService,
                                         final GuildService guildService,
                                         final CityService cityService) {
        this.serverObjectService = serverObjectService;
        this.guildService = guildService;
        this.cityService = cityService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final ChatAvatarConnected message) throws Exception {
        final long networkId = message.getCharacterId();

        final ServerObject obj = serverObjectService.get(networkId);

        if (obj != null) {
            final CreatureObject creatureObject = obj.asCreatureObject();

            if (creatureObject != null) {
                LOGGER.debug("Character {}({}) connected to chat. Entering guild and city rooms.",
                        creatureObject.getAssignedObjectName(),
                        networkId);

                if (creatureObject.isInGuild())
                    guildService.enterGuildChatRoom(creatureObject);

                final TIntList cities = cityService.getCitizensCities(creatureObject.getNetworkId());

                if (!cities.isEmpty())
                    cityService.enterCityChatRoom(cities, creatureObject);

                //TODO: Post chat server connect script hook.
            } else {
                LOGGER.warn("Attempted to handle ChatAvatarConnected message for characterId {}, but it was not a CreatureObject.",
                        networkId);
            }
        } else {
            LOGGER.warn("Attempted to handle ChatAvatarConnected message for characterId {}, but it was not a ServerObject.",
                    networkId);
        }
    }
}
