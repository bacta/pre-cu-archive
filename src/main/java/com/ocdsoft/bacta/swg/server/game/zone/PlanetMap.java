package com.ocdsoft.bacta.swg.server.game.zone;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.TreeMap;

@Singleton
public class PlanetMap extends TreeMap<String, Zone> implements ZoneMap {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Inject
    public PlanetMap(BactaConfiguration bactaConfiguration) {

        Collection<String> activeZones = bactaConfiguration.getStringCollection("Bacta/Zones", "Zone");
        for(String zone : activeZones) {
            logger.info("Loading Planet: " + zone);
            String terrain = bactaConfiguration.getString("Bacta/Zones/" + zone, "Terrain");

            // TODO: TERRAIN loading
            Planet newPlanet = new Planet(this, zone.toLowerCase()/*, null*/);

            put(zone.toLowerCase(), newPlanet);

        }
    }

    @Override
    public void addObject(Zone zone, TangibleObject obj) {

    }

    @Override
    public void removeObject(Zone zone, TangibleObject obj) {

    }
}
