package com.ocdsoft.bacta.swg.precu.controller.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.lang.Gender;
import com.ocdsoft.bacta.swg.lang.Race;
import com.ocdsoft.bacta.swg.localization.StringId;
import com.ocdsoft.bacta.swg.name.NameService;
import com.ocdsoft.bacta.swg.precu.message.game.client.ClientRandomNameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.precu.message.game.client.ClientRandomNameRequest;

@MessageHandled(handles = ClientRandomNameRequest.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientRandomNameRequestController implements GameNetworkMessageController<ClientRandomNameRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRandomNameRequestController.class);

    private final NameService nameService;

    @Inject
    public ClientRandomNameRequestController(NameService nameService) {
        this.nameService = nameService;
    }


    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientRandomNameRequest message) {

        Race race = Race.parseRace(message.getCreatureTemplate());
        Gender gender = Gender.parseGender(message.getCreatureTemplate());

        String fullName = nameService.generateName(NameService.PLAYER, race, gender);

        connection.sendMessage(
                new ClientRandomNameResponse(
                        message.getCreatureTemplate(),
                        fullName,
                        new StringId("ui", "name_approved")
                )
        );    }

}

