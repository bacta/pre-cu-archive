package com.ocdsoft.bacta.swg.server.game.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.service.AccountService;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.message.TerminateReason;
import com.ocdsoft.bacta.swg.server.login.object.SoeAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to validate that a connection is the owner for a character
 * Created by kyle on 5/29/2016.
 */
@Singleton
public final class AccountSecurityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountSecurityService.class);

    private final AccountService<SoeAccount> accountService;

    @Inject
    public AccountSecurityService(final AccountService<SoeAccount> accountService) {
        this.accountService = accountService;
    }

    /**
     * Verify that the current connection is the owner of the specified characterId
     * @param connection current connection
     * @param characterId id of the desired character
     * @return
     */
    public boolean verifyCharacterOwnership(final SoeUdpConnection connection, final long characterId) {

        SoeAccount account = accountService.getAccount(connection.getAccountUsername());
        if (account == null) {
            LOGGER.warn("verifyCharacterOwnership attempted from {} failed: Username '{}' doesn't exist", connection.getRemoteAddress(), connection.getAccountUsername());
            connection.terminate(TerminateReason.REFUSED);
            return false;
        }

        if(!account.hasCharacter(characterId)) {
            LOGGER.warn("verifyCharacterOwnership attempted from {} using username '{}' that is not the account that owns Character {}", connection.getRemoteAddress(), connection.getAccountUsername(), characterId);
            connection.terminate(TerminateReason.REFUSED);
            return false;
        }

        return true;
    }
}
