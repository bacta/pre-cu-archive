package com.ocdsoft.bacta.swg.server.game.guild;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/27/2016.
 */
@Singleton
public class GuildService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuildService.class);

    //Map of guilds.

    @Inject
    public GuildService() {

    }

    public void notifyChatRoomCreated(final String roomPath) {
    }

    public void enterGuildChatRoom(final CreatureObject creatureObject) {
        LOGGER.debug("Creature {} entering guild chat rooms.", creatureObject.getNetworkId());
        LOGGER.warn("Not implemented");
    }

    public void sendTo(final CreatureObject creatureObject) {
        LOGGER.warn("Not implemented");
    }
}
