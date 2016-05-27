package com.ocdsoft.bacta.swg.server.game.controller.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.message.game.client.*;
import com.ocdsoft.bacta.swg.server.game.message.game.scene.CmdStartScene;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
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

    private final ObjectService<ServerObject> objectService;
    private final GameServerState serverState;
    private final ZoneMap zoneMap;

    @Inject
    public SelectCharacterController(final ObjectService<ServerObject> objectService,
                                     final GameServerState serverState,
                                     final ZoneMap zoneMap) {

        this.objectService = objectService;
        this.serverState = serverState;
        this.zoneMap = zoneMap;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SelectCharacter message) {

        CreatureObject character = objectService.get(message.getCharacterId());
        if (character != null) {

            connection.setCurrentNetworkId(character.getNetworkId());
            connection.setCurrentCharName(character.getAssignedObjectName());

            character.setConnection(connection);

            ChatServerStatus flag = new ChatServerStatus(0);
            connection.sendMessage(flag);

            Zone zone = zoneMap.get("tatooine");

            CmdStartScene start = new CmdStartScene(
                    false,
                    character.getNetworkId(),
                    zone.getTerrainFile(),
                    character.getTransformObjectToWorld().getPositionInParent(),
                    character.getObjectFrameKInWorld().theta(),
                    character.getSharedTemplate().getResourceName(),
                    0,
                    0
            );

            connection.sendMessage(start);

            ServerTimeMessage serverTimeMessage = new ServerTimeMessage(0);
            connection.sendMessage(serverTimeMessage);

            //TODO: Weather update interval
            ParametersMessage parammessage = new ParametersMessage(0x00000384);
            connection.sendMessage(parammessage);

            //UpdatePvpStatusMessage pvpStatusMessage = new UpdatePvpStatusMessage(character.getNetworkId(), )

            connection.sendMessage(new ChatOnConnectAvatar());

//			GuildObject guildObject = new GuildObject(); //TODO one global GuildObject list for server.
//			guildObject.setClientCRC(0x7D40E2E6);
//			guildObject.setNetworkId(400);
//
//			guildObject.sendTo(character);

            Set<SoeUdpConnection> user = new HashSet<>();
            user.add(connection);
            character.sendCreateAndBaselinesTo(user);
            zone.add(character);

        } else {
            LOGGER.error("Unable to lookup character {} ", message.getCharacterId());
        }
    }
}

