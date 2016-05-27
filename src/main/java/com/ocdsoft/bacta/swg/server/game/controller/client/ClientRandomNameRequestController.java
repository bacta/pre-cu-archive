package com.ocdsoft.bacta.swg.server.game.controller.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.creation.ClientRandomNameRequest;
import com.ocdsoft.bacta.swg.server.game.message.creation.ClientRandomNameResponse;
import com.ocdsoft.bacta.swg.server.game.name.NameService;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

        final Race race = Race.parseRace(message.getCreatureTemplate());
        final Gender gender = Gender.parseGender(message.getCreatureTemplate());

        final String fullName = nameService.generateName(NameService.PLAYER, race, gender);

        connection.sendMessage(
                new ClientRandomNameResponse(
                        message.getCreatureTemplate(),
                        fullName,
                        new StringId("ui", "name_approved")
                )
        );
    }

}

