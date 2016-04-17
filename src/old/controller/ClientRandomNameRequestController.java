package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.ClientRandomNameRequest;
import com.ocdsoft.bacta.swg.precu.message.zone.ClientRandomNameResponse;
import com.ocdsoft.bacta.swg.precu.service.name.NameService;
import com.ocdsoft.bacta.swg.precu.util.Gender;
import com.ocdsoft.bacta.swg.precu.util.Race;

@GameNetworkMessageHandled(server = ServerType.GAME, handles = ClientRandomNameRequest.class)
public class ClientRandomNameRequestController implements GameNetworkMessageController<GameClient> {

    private final NameService nameService;

    @Inject
    public ClientRandomNameRequestController(NameService nameService) {
        this.nameService = nameService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, SoeByteBuf message) {

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
