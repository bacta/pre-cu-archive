package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.message.SelectCharacter;
import com.ocdsoft.bacta.swg.server.game.message.chat.ChatOnConnectAvatar;
import com.ocdsoft.bacta.swg.server.game.message.zone.CmdStartSceneMessage;
import com.ocdsoft.bacta.swg.server.game.message.zone.ParametersMessage;
import com.ocdsoft.bacta.swg.server.game.message.zone.UnkByteFlag;
import com.ocdsoft.bacta.swg.server.game.object.SceneObject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import com.ocdsoft.bacta.swg.server.game.zone.Zone;
import com.ocdsoft.bacta.swg.server.game.zone.ZoneMap;
import com.ocdsoft.network.service.object.ObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = SelectCharacter.class)
public class SelectCharacterController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    private ObjectService<SceneObject> objectService;

    @Inject
    private GameServerState serverState;

    @Inject
    private ZoneMap zoneMap;

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        long characterObjectId = message.readLong();
        CreatureObject character = objectService.get(characterObjectId);

        if (character != null) {

            client.setCharacter(character);
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
