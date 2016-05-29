package com.ocdsoft.bacta.swg.server.game.city;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/27/2016.
 */
@Singleton
public class CityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);

    @Inject
    public CityService() {

    }

    public void notifyChatRoomCreated(final String roomPath) {
    }

    public TIntList getCitizensCities(final long citizenId) {
        LOGGER.warn("Not implemented");
        return new TIntArrayList();
    }

    /**
     * Loads a citizen into the city chat room for the given cities.
     *
     * @param cityIds A list of city ids for which this citizen should try to join their chat rooms.
     * @param citizen The citizen joining.
     */
    public void enterCityChatRoom(final TIntList cityIds, final CreatureObject citizen) {
        LOGGER.warn("Not implemented");
    }
}
