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
public class CreatureState implements SharedFileLoader {
    private static final String dataTableName = "datatables/include/state.iff";
    private static final Logger logger = LoggerFactory.getLogger(CreatureState.class);

    private final TObjectIntMap<String> states = new TObjectIntHashMap<>();

    private final DataTableManager dataTableManager;

    @Inject
    public CreatureState(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    private void load() {
        logger.trace("Loading creature states datatable.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            states.put(
                    dataTable.getStringValue("state", row),
                    dataTable.getIntValue("value", row));
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d creature states.", states.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            states.clear();
            load();
        }
    }
}
