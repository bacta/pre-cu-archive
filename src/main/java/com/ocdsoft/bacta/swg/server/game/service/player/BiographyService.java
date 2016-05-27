package com.ocdsoft.bacta.swg.server.game.service.player;

import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kyle on 5/9/2016.
 */
@Singleton
public class BiographyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerObject.class);

    public void setBiography(long networkId, String biography) {
        // TODO: Implement Biographies
        LOGGER.error("Not implemented yet");
    }
}
