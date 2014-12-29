package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.ClientRandomNameRequest;
import com.ocdsoft.bacta.swg.server.game.message.zone.ClientRandomNameResponse;
import com.ocdsoft.bacta.swg.server.game.service.name.NameService;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;

@SwgController(server = ServerType.GAME, handles = ClientRandomNameRequest.class)
public class ClientRandomNameRequestController implements SwgMessageController<GameClient> {

    private final NameService nameService;

    @Inject
    public ClientRandomNameRequestController(NameService nameService) {
        this.nameService = nameService;
    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        String raceString = message.readAscii();

        Race race = parseRace(raceString);
        Gender gender = parseGender(raceString);

        String fullName = nameService.generateName(NameService.PLAYER, race, gender);

        client.sendMessage(new ClientRandomNameResponse(fullName, raceString));
    }

    private Race parseRace(String raceString) {
        String race = raceString.substring(raceString.lastIndexOf("/") + 1, raceString.lastIndexOf("_"));
        return Race.valueOf(race.toUpperCase());
    }

    private Gender parseGender(String raceString) {
        String gender = raceString.substring(raceString.lastIndexOf("_") + 1, raceString.indexOf(".iff"));
        return Gender.valueOf(gender.toUpperCase());
    }
}
