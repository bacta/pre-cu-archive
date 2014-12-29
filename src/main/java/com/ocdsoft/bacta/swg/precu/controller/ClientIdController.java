package com.ocdsoft.bacta.swg.precu.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.network.soe.buffer.SoeByteBuf;
import com.ocdsoft.bacta.swg.network.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.swg.network.swg.ServerType;
import com.ocdsoft.bacta.swg.network.swg.SwgController;
import com.ocdsoft.bacta.swg.network.swg.controller.SwgMessageController;
import com.ocdsoft.bacta.swg.server.game.GameClient;
import com.ocdsoft.bacta.swg.server.game.message.ClientPermissionsMessage;
import com.ocdsoft.bacta.swg.server.game.message.zone.ClientIdMsg;
import com.ocdsoft.network.security.authenticator.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server = ServerType.GAME, handles = ClientIdMsg.class)
public class ClientIdController implements SwgMessageController<GameClient> {

    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private AccountService<SoeAccount> accountService;

    @Inject
    public ClientIdController(AccountService<SoeAccount> accountService) {

        this.accountService = accountService;

    }

    @Override
    public void handleIncoming(GameClient client, SoeByteBuf message) {

        int unknown1 = message.readInt();

        String authToken = message.readBinaryString();
        String clientVersion = message.readAscii();

        // Validate client version
        if (!isRequiredVersion(clientVersion)) {
            client.sendErrorMessage("Login Error", "The client you are attempting to connect with does not match that required by the server.", false);
            logger.info("Sending Client Error");
            return;
        }

        SoeAccount account = accountService.validateSession(authToken);
        if (account == null) {
            client.sendErrorMessage("Error", "Invalid Session", false);
            logger.info("Invalid Session: " + authToken);
            return;
        }

        client.setAccountId(account.getId());
        client.setAccountUsername(account.getUsername());

        ClientPermissionsMessage cpm = new ClientPermissionsMessage(account, true, true, true, true);
        client.sendMessage(cpm);
    }

    private boolean isRequiredVersion(String clientVersion) {
        return clientVersion.equals("20050408-18:00");
    }

}
