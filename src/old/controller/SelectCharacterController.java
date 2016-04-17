package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.swg.precu.message.SelectCharacter;
import com.ocdsoft.bacta.swg.precu.message.chat.ChatOnConnectAvatar;
import com.ocdsoft.bacta.swg.precu.message.zone.CmdStartSceneMessage;
import com.ocdsoft.bacta.swg.precu.message.zone.ParametersMessage;
import com.ocdsoft.bacta.swg.precu.message.zone.UnkByteFlag;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.precu.zone.Zone;
import com.ocdsoft.bacta.swg.precu.zone.ZoneMap;
import com.ocdsoft.network.service.object.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = SelectCharacter.class)
public class SelectCharacterController implements GameNetworkMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    private ObjectService<SceneObject> objectService;

    @Inject
    private GameServerState serverState;

    @Inject
    private ZoneMap zoneMap;

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {

        long characterObjectId = message.readLong();
        CreatureObject character = objectService.get(characterObjectId);

        if (character != null) {

            connection.setCurrentNetworkId(character.getNetworkId());
            connection.setCurrentCharName(character.getObjectName().getString());

            character.setClient(client);

            UnkByteFlag flag = new UnkByteFlag();
            client.sendMessage(flag);

            Zone zone = zoneMap.get("tatooine");

            CmdStartSceneMessage start = new CmdStartSceneMessage(character, zone.getTerrainFile(), 0);
            client.sendMessage(start);

            ParametersMessage parammessage = new ParametersMessage();
            client.sendMessage(parammessage);

            client.sendMessage(new ChatOnConnectAvatar());

//			GuildObject guildObject = new GuildObject(); //TODO one global GuildObject list for server.
//			guildObject.setClientCRC(0x7D40E2E6);
//			guildObject.setNetworkId(400);
//
//			guildObject.sendTo(character);

//            character.sendTo(client);
            zone.add(character);

        } else {
            logger.error("Unable to lookup character oid: " + characterObjectId);
        }
    }
}
