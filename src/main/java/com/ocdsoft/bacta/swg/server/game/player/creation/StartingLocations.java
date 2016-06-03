package com.ocdsoft.bacta.swg.server.game.player.creation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.service.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class StartingLocations implements SharedFileLoader {
    private static final String dataTableName = "datatables/creation/starting_locations.iff";
    private static final Logger logger = LoggerFactory.getLogger(StartingLocations.class);

    private final Map<String, StartingLocationInfo> startingLocations = new HashMap<>();
    private final DataTableManager dataTableManager;

    @Inject
    public StartingLocations(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * Gets a {@link StartingLocationInfo} object
     * specified by a string key representing the location.
     *
     * @param location The key that represents the location. i.e. mos_eisley
     * @return {@link StartingLocationInfo} object
     * if key exists. Otherwise, null.
     */
    public StartingLocationInfo getStartingLocationInfo(final String location) {
        return startingLocations.get(location);
    }

    private void load() {
        logger.trace("Loading starting locations.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            final StartingLocationInfo locationInfo = new StartingLocationInfo(dataTable, row);
            startingLocations.put(locationInfo.location, locationInfo);
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d starting locations.", startingLocations.size()));
    }

    public void reload() {
        synchronized (this) {
            startingLocations.clear();
            load();
        }
    }

    public static final class StartingLocationInfo {
        @Getter
        private final String location;
        @Getter
        private final String planet;
        @Getter
        private final float x;
        @Getter
        private final float y;
        @Getter
        private final float z;
        @Getter
        private final String cellId;
        @Getter
        private final String image;
        @Getter
        private final String description;
        @Getter
        private final float radius;
        @Getter
        private final float heading;

        public StartingLocationInfo(final DataTable dataTable, final int row) {
            location = dataTable.getStringValue("location", row);
            planet = dataTable.getStringValue("planet", row);
            x = dataTable.getFloatValue("x", row);
            y = dataTable.getFloatValue("y", row);
            z = dataTable.getFloatValue("z", row);
            cellId = dataTable.getStringValue("cellId", row);
            image = dataTable.getStringValue("image", row);
            description = dataTable.getStringValue("description", row);
            radius = dataTable.getFloatValue("radius", row);
            heading = dataTable.getFloatValue("heading", row);
        }
    }
}
