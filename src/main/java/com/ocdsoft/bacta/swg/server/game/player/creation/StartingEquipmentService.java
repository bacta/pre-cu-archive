package com.ocdsoft.bacta.swg.server.game.player.creation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.tre.TreeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 6/3/2016.
 * <p>
 * Loads all of the starting equipment, and handles adding it to a player.
 */
@Singleton
public final class StartingEquipmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartingEquipmentService.class);

    private final TreeFile treeFile;

    @Inject
    public StartingEquipmentService(final TreeFile treeFile) {
        this.treeFile = treeFile;
    }

    public void loadStartingEquipment() {

    }
}
