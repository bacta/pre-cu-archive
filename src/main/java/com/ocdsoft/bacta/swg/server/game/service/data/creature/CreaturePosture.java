package com.ocdsoft.bacta.swg.server.game.service.data.creature;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.service.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/30/14.
 */
@Singleton
public class CreaturePosture implements SharedFileLoader {
    private static final String dataTableName = "datatables/include/posture.iff";
    private static final Logger logger = LoggerFactory.getLogger(CreaturePosture.class);

    private final TObjectIntMap<String> postures = new TObjectIntHashMap<>();

    private final DataTableManager dataTableManager;

    @Inject
    public CreaturePosture(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    private void load() {
        logger.trace("Loading creature postures datatable.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            postures.put(
                    dataTable.getStringValue("posture", row),
                    dataTable.getIntValue("value", row));
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d creature postures.", postures.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            postures.clear();
            load();
        }
    }
}
