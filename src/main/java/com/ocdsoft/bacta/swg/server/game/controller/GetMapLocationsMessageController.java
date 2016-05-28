package com.ocdsoft.bacta.swg.server.game.controller;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.controller.ConnectionRolesAllowed;
import com.ocdsoft.bacta.soe.controller.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.controller.MessageHandled;
import com.ocdsoft.bacta.swg.server.game.message.planet.GetMapLocationsMessage;
import com.ocdsoft.bacta.swg.server.game.message.planet.GetMapLocationsResponseMessage;
import com.ocdsoft.bacta.swg.server.game.object.universe.planet.PlanetObject;
import com.ocdsoft.bacta.swg.server.game.planet.MapLocationType;
import com.ocdsoft.bacta.swg.server.game.planet.PlanetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@MessageHandled(handles = GetMapLocationsMessage.class)
@ConnectionRolesAllowed({ConnectionRole.AUTHENTICATED})
public class GetMapLocationsMessageController implements GameNetworkMessageController<GetMapLocationsMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetMapLocationsMessageController.class);

    private final PlanetService planetService;

    @Inject
    public GetMapLocationsMessageController(final PlanetService planetService) {
        this.planetService = planetService;
    }

    @Override
    public void handleIncoming(final SoeUdpConnection connection, final GetMapLocationsMessage message) {
        final PlanetObject planetObject = planetService.getPlanetObject(message.getPlanetName());

        final GetMapLocationsResponseMessage response;

        if (planetObject == null) {
            LOGGER.error("Requested map locations for a non-existent, or un-initialized, planet object {}", message.getPlanetName());

            response = new GetMapLocationsResponseMessage(
                    message.getPlanetName(),
                    new ArrayList<>(0),
                    new ArrayList<>(0),
                    new ArrayList<>(0),
                    0,
                    0,
                    0);
        } else {
            response = new GetMapLocationsResponseMessage(
                    planetObject.getPlanetName(),
                    planetObject.getMapLocations(MapLocationType.STATIC),
                    planetObject.getMapLocations(MapLocationType.DYNAMIC),
                    planetObject.getMapLocations(MapLocationType.PERSIST),
                    planetObject.getMapLocationVersionForType(MapLocationType.STATIC),
                    planetObject.getMapLocationVersionForType(MapLocationType.DYNAMIC),
                    planetObject.getMapLocationVersionForType(MapLocationType.PERSIST));
        }

        connection.sendMessage(response);
    }
}

