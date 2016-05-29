package com.ocdsoft.bacta.swg.server.game.controller.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.service.AccountService;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.io.udp.AccountCache;
import com.ocdsoft.bacta.soe.io.udp.NetworkConfiguration;
import com.ocdsoft.bacta.soe.io.udp.SubscriptionService;
import com.ocdsoft.bacta.swg.server.login.object.SoeAccount;
import com.ocdsoft.bacta.swg.server.game.message.ErrorMessage;
import com.ocdsoft.bacta.swg.server.game.message.client.ClientIdMsg;
import com.ocdsoft.bacta.swg.server.game.message.client.ClientPermissionsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = ClientIdMsg.class)
@ConnectionRolesAllowed({})
public class ClientIdMsgController implements GameNetworkMessageController<ClientIdMsg> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientIdMsgController.class);

    private final AccountService<SoeAccount> accountService;
    private final AccountCache accountCache;
    private final NetworkConfiguration configuration;
    private final SubscriptionService subscriptionService;

    @Inject
    public ClientIdMsgController(final AccountService<SoeAccount> accountService,
                                 final AccountCache accountCache,
                                 final NetworkConfiguration configuration,
                                 final SubscriptionService subscriptionService) {

        this.accountService = accountService;
        this.accountCache = accountCache;
        this.configuration = configuration;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientIdMsg message) {
        // Validate client version
        if (!message.getClientVersion().equals(configuration.getRequiredClientVersion())) {
            ErrorMessage error = new ErrorMessage("Login Error", "The client you are attempting to connect with does not match that required by the server.", false);
            connection.sendMessage(error);
            LOGGER.info("Sending Client Error");
            return;
        }

        SoeAccount account = accountService.validateSession(connection.getRemoteAddress().getAddress(), message.getToken());
        if (account == null) {
            ErrorMessage error = new ErrorMessage("Error", "Invalid Session", false);
            connection.sendMessage(error);
            LOGGER.info("Invalid Session: " + message.getToken());
            return;
        }

        connection.setBactaId(account.getId());
        connection.setAccountUsername(account.getUsername());
        connection.addRole(ConnectionRole.AUTHENTICATED);

        accountCache.addAccountConnection(connection);
        subscriptionService.onConnect(connection);

        // TODO: Actually implement permissions
        ClientPermissionsMessage cpm = new ClientPermissionsMessage(true, true, true, false);
        connection.sendMessage(cpm);
    }
}

