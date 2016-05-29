package com.ocdsoft.bacta.swg.server.game.controller.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.chat.GameChatService;
import com.ocdsoft.bacta.swg.server.game.guild.GuildService;
import com.ocdsoft.bacta.swg.server.game.message.client.ParametersMessage;
import com.ocdsoft.bacta.swg.server.game.message.client.SelectCharacter;
import com.ocdsoft.bacta.swg.server.game.message.client.ServerTimeMessage;
import com.ocdsoft.bacta.swg.server.game.message.scene.CmdStartScene;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.service.AccountSecurityService;
import com.ocdsoft.bacta.swg.server.game.zone.Zone;
import com.ocdsoft.bacta.swg.server.game.zone.ZoneMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@MessageHandled(handles = SelectCharacter.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class SelectCharacterController implements GameNetworkMessageController<SelectCharacter> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectCharacterController.class);

    private final AccountSecurityService accountSecurityService;
    private final ObjectService<ServerObject> objectService;
    private final GuildService guildService;
    private final GameServerState serverState;
    private final GameChatService chatService;
    private final ZoneMap zoneMap;

    @Inject
    public SelectCharacterController(final AccountSecurityService accountSecurityService,
                                     final ObjectService<ServerObject> objectService,
                                     final GuildService guildService,
                                     final GameServerState serverState,
                                     final GameChatService chatService,
                                     final ZoneMap zoneMap) {

        this.accountSecurityService = accountSecurityService;
        this.objectService = objectService;
        this.guildService = guildService;
        this.chatService = chatService;
        this.serverState = serverState;
        this.zoneMap = zoneMap;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final SelectCharacter message) {

        if(accountSecurityService.verifyCharacterOwnership(connection, message.getCharacterId())) {

            CreatureObject character = objectService.get(message.getCharacterId());
            if (character != null) {

                connection.setCurrentNetworkId(character.getNetworkId());
                connection.setCurrentCharName(character.getAssignedObjectName());

                character.setConnection(connection);

                //Tell the ChatService to start connecting this character.
                chatService.connectAvatar(character);

                final Zone zone = zoneMap.get("tatooine");

                final CmdStartScene start = new CmdStartScene(
                        false,
                        character.getNetworkId(),
                        zone.getTerrainFile(),
                        character.getTransformObjectToWorld().getPositionInParent(),
                        character.getObjectFrameKInWorld().theta(),
                        character.getSharedTemplate().getResourceName(),
                        0,
                        0);

                connection.sendMessage(start);

                final ServerTimeMessage serverTimeMessage = new ServerTimeMessage(0);
                connection.sendMessage(serverTimeMessage);

                //TODO: Weather update interval
                final ParametersMessage parametersMessage = new ParametersMessage(0x00000384);
                connection.sendMessage(parametersMessage);

                //Send guild object to character.
                guildService.sendTo(character);

                final Set<SoeUdpConnection> user = new HashSet<>();
                user.add(connection);

                character.sendCreateAndBaselinesTo(user);
                zone.add(character);

            } else {
                LOGGER.error("Unable to lookup character {} ", message.getCharacterId());
            }
        }
    }
}

