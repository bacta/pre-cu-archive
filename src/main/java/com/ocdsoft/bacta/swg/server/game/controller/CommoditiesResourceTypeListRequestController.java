package com.ocdsoft.bacta.swg.server.game.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.commodities.CommoditiesMarketService;
import com.ocdsoft.bacta.swg.server.game.message.CommoditiesResourceTypeListRequest;
import com.ocdsoft.bacta.swg.server.game.message.CommoditiesResourceTypeListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/26/2016.
 */
@MessageHandled(handles = CommoditiesResourceTypeListRequest.class)
@ConnectionRolesAllowed(value = ConnectionRole.AUTHENTICATED)
public class CommoditiesResourceTypeListRequestController implements GameNetworkMessageController<CommoditiesResourceTypeListRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommoditiesResourceTypeListRequestController.class);

    private final GameServerState gameServerState;
    private final CommoditiesMarketService commoditiesMarketService;

    @Inject
    public CommoditiesResourceTypeListRequestController(final GameServerState gameServerState,
                                                        final CommoditiesMarketService commoditiesMarketService) {
        this.gameServerState = gameServerState;
        this.commoditiesMarketService = commoditiesMarketService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final CommoditiesResourceTypeListRequest message) throws Exception {
        LOGGER.warn("Received version number {}", message.getResourceTypeMapVersionNumber());

        final String resourceTypeMapVersionNumber = String.format("%s.%d",
                gameServerState.getClusterServer().getName(),
                commoditiesMarketService.getItemTypeMapVersionNumber());

        if (!resourceTypeMapVersionNumber.equals(message.getResourceTypeMapVersionNumber())) {
            LOGGER.info("Sending new version number {}", resourceTypeMapVersionNumber);

            final CommoditiesResourceTypeListResponse response = new CommoditiesResourceTypeListResponse(
                    resourceTypeMapVersionNumber,
                    commoditiesMarketService.getResourceTypeMap());

            connection.sendMessage(response);
        }
    }
}