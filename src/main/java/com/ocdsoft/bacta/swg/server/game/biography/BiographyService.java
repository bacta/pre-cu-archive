package com.ocdsoft.bacta.swg.server.game.biography;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.server.game.controller.object.ObjControllerBuilder;
import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueBiographyPayload;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.CreatureObject;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/1/2016.
 * <p>
 * Caches biographies and handles requests/sets for bios to the database.
 */
@Singleton
public final class BiographyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BiographyService.class);
    private static final String DEFAULT_BACTA_BIO = "Bacta biography.";

    private final TLongObjectMap<CachedBiography> cachedBiographyMap;

    public BiographyService() {
        this.cachedBiographyMap = new TLongObjectHashMap<>();
    }

    public void setBiography(final long networkId, final String biography) {
        LOGGER.error("Not implemented");
        //write to the database.
        //cache.
    }

    public void deleteBiography(final long networkId) {
        LOGGER.error("Not implemented");
    }

    public void requestBiography(final long biographyOwnerId, final CreatureObject requestorObject) {
        final SoeUdpConnection connection = requestorObject.getConnection();

        if (connection != null) {
            final MessageQueueBiographyPayload payload = new MessageQueueBiographyPayload(
                    new BiographyPayload(biographyOwnerId, DEFAULT_BACTA_BIO));

            final ObjControllerMessage msg = ObjControllerBuilder.newBuilder()
                    .send().reliable().authClient()
                    .build(biographyOwnerId, GameControllerMessageType.BIOGRAPHY_RETRIEVED, payload);

            connection.sendMessage(msg);
        }
    }

    @AllArgsConstructor
    private final static class CachedBiography {
        private final long cacheTimeStamp;
        private final String biography;
    }
}
