package com.ocdsoft.bacta.swg.server.game.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.GameServerState;
import com.ocdsoft.bacta.swg.server.game.commodities.CommoditiesMarketService;
import com.ocdsoft.bacta.swg.server.game.message.CommoditiesItemTypeListRequest;
import com.ocdsoft.bacta.swg.server.game.message.CommoditiesItemTypeListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageHandled(handles = CommoditiesItemTypeListRequest.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class CommoditiesItemTypeListRequestController implements GameNetworkMessageController<CommoditiesItemTypeListRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommoditiesItemTypeListRequestController.class);

    private final GameServerState gameServerState;
    private final CommoditiesMarketService commoditiesMarketService;

    @Inject
    public CommoditiesItemTypeListRequestController(final GameServerState gameServerState,
                                                    final CommoditiesMarketService commoditiesMarketService) {
        this.gameServerState = gameServerState;
        this.commoditiesMarketService = commoditiesMarketService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final CommoditiesItemTypeListRequest message) {
        LOGGER.warn("Received version number {}", message.getItemTypeMapVersionNumber());

        final String itemTypeMapVersionNumber = String.format("%s.%d",
                gameServerState.getClusterServer().getName(),
                commoditiesMarketService.getItemTypeMapVersionNumber());

        if (!itemTypeMapVersionNumber.equals(message.getItemTypeMapVersionNumber())) {
            LOGGER.info("Sending new version number {}", itemTypeMapVersionNumber);

            final CommoditiesItemTypeListResponse response = new CommoditiesItemTypeListResponse(
                    itemTypeMapVersionNumber,
                    commoditiesMarketService.getItemTypeMap());

            connection.sendMessage(response);
        }
    }
}