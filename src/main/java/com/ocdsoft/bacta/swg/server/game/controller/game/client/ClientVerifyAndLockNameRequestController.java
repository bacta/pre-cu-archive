package com.ocdsoft.bacta.swg.server.game.controller.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.game.creation.ClientVerifyAndLockNameRequest;
import com.ocdsoft.bacta.swg.server.game.message.game.creation.ClientVerifyAndLockNameResponse;
import com.ocdsoft.bacta.swg.server.game.name.NameService;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ClientVerifyAndLockNameRequest.class)
@ConnectionRolesAllowed({})
public class ClientVerifyAndLockNameRequestController implements GameNetworkMessageController<ClientVerifyAndLockNameRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientVerifyAndLockNameRequestController.class);

    private final NameService nameService;

    @Inject
    public ClientVerifyAndLockNameRequestController(final NameService nameService) {
        this.nameService = nameService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientVerifyAndLockNameRequest message) {

        StringId errorMessage;
        Race race = Race.parseRace(message.getTemplateName());
        Gender gender = Gender.parseGender(message.getTemplateName());

        String errorString = nameService.validateName(NameService.PLAYER, message.getCharacterName(), race, gender);
        if(errorString.isEmpty()) {
            errorString = "name_approved";
        }

        errorMessage = new StringId("ui", errorString);

        ClientVerifyAndLockNameResponse response = new ClientVerifyAndLockNameResponse(
                message.getCharacterName(),
                errorMessage
        );
        connection.sendMessage(response);
    }
}

