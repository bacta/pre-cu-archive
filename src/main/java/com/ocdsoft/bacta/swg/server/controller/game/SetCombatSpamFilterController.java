package com.ocdsoft.bacta.swg.server.controller.game;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.message.game.SetCombatSpamFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = SetCombatSpamFilter.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class SetCombatSpamFilterController implements GameNetworkMessageController<SetCombatSpamFilter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetCombatSpamFilterController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final SetCombatSpamFilter message) throws Exception {
        LOGGER.warn("Not implemented");
    }
}