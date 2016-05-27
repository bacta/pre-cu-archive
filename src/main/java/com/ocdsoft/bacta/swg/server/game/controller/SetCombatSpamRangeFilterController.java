package com.ocdsoft.bacta.swg.server.game.controller;

import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.SetCombatSpamRangeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = SetCombatSpamRangeFilter.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class SetCombatSpamRangeFilterController implements GameNetworkMessageController<SetCombatSpamRangeFilter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetCombatSpamRangeFilterController.class);

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final SetCombatSpamRangeFilter message) throws Exception {
        LOGGER.warn("Not implemented");
    }
}