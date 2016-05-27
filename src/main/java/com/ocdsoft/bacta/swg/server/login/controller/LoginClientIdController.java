package com.ocdsoft.bacta.swg.server.login.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.soe.io.udp.MessageSubscriptionService;
import com.ocdsoft.bacta.swg.server.login.object.SoeAccount;
import com.ocdsoft.bacta.swg.server.login.message.*;
import com.ocdsoft.bacta.swg.server.login.service.ClusterService;
import com.ocdsoft.bacta.swg.server.game.message.ErrorMessage;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = LoginClientId.class, type = ServerType.LOGIN)
@ConnectionRolesAllowed({})
public class LoginClientIdController implements GameNetworkMessageController<LoginClientId> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginClientIdController.class);

    private int timezone;
    private ClusterService clusterService;
    private AccountService<SoeAccount> accountService;
    private String requiredClientVersion;
    private final MessageSubscriptionService messageSubscriptionService;

    @Inject
    public LoginClientIdController(final BactaConfiguration configuration,
                                   final ClusterService clusterService,
                                   final AccountService<SoeAccount> accountService,
                                   final MessageSubscriptionService messageSubscriptionService) {

        this.clusterService = clusterService;
        this.accountService = accountService;
        this.messageSubscriptionService = messageSubscriptionService;
        requiredClientVersion = configuration.getString("Bacta/GameServer", "ClientVersion");
        timezone = DateTimeZone.getDefault().getOffset(null) / 1000;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, LoginClientId message) {

        // Validate client version
        if (!isRequiredVersion(message.getClientVersion())) {
            ErrorMessage error = new ErrorMessage("Login Error", "The client you are attempting to connect with does not match that required by the server.", false);
            connection.sendMessage(error);
            LOGGER.debug("Client attempted to connect with client version {} when {} is required", message.getClientVersion(), requiredClientVersion);
            return;
        }

        if (message.getPassword().isEmpty()) {
            ErrorMessage error = new ErrorMessage("Login Error", "Please enter a password.", false);
            connection.sendMessage(error);
            return;
        }

        SoeAccount account = null;
        try {

            account = accountService.getAccount(message.getUsername());

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage("Login Error", "Duplicate Accounts in the database", false);
            connection.sendMessage(error);
            LOGGER.error("Duplicate accounts in database", e);

            return;
        }

        if (account == null) {
            account = accountService.createAccount(message.getUsername(), message.getPassword());
            if (account == null) {
                ErrorMessage error = new ErrorMessage("Login Error", "Unable to create account.", false);
                connection.sendMessage(error);
                return;
            }
        } else if (!accountService.authenticate(account, message.getPassword())) {
            ErrorMessage error = new ErrorMessage("Login Error", "Invalid username or password.", false);
            connection.sendMessage(error);
            return;
        }

        accountService.createAuthToken(account);
        connection.setAccountId(account.getId());
        connection.setAccountUsername(account.getUsername());

        LoginClientToken token = new LoginClientToken(account.getAuthToken(), account.getId(), account.getUsername());
        connection.sendMessage(token);

        LoginEnumCluster cluster = new LoginEnumCluster(clusterService.getClusterEntries(), timezone);
        connection.sendMessage(cluster);

        LoginClusterStatus status = new LoginClusterStatus(clusterService.getClusterEntries());
        connection.sendMessage(status);

        EnumerateCharacterId characters = new EnumerateCharacterId(account);
        connection.sendMessage(characters);

        messageSubscriptionService.onConnect(connection);
    }

    private boolean isRequiredVersion(String clientVersion) {
        return clientVersion.equals(requiredClientVersion);
    }
}

