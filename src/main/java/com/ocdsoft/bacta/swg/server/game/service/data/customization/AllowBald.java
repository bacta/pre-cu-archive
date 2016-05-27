package com.ocdsoft.bacta.swg.server.game.service.data.customization;

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
public class AllowBald implements SharedFileLoader {
    private static final String dataTableName = "datatables/customization/allow_bald.iff";
    private static final Logger logger = LoggerFactory.getLogger(AllowBald.class);

    private final TObjectIntMap<String> allowBald = new TObjectIntHashMap<>();

    private final DataTableManager dataTableManager;

    @Inject
    public AllowBald(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * Can a specified player template be bald or not. Takes a filename, not a filepath. i.e. moncal_female.
     *
     * @param playerTemplate Filename, not filepath. i.e. moncal_female
     * @return Returns true if they are allowed to be bald, otherwise false.
     */
    public boolean isAllowedBald(final String playerTemplate) {
        if (!allowBald.containsKey(playerTemplate))
            return false;

        return allowBald.get(playerTemplate) == 1;
    }

    private void load() {
        logger.trace("Loading allow bald player template settings.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            allowBald.put(
                    dataTable.getStringValue("SPECIES_GENDER", row), //Key
                    dataTable.getIntValue("ALLOW_BALD", row));   //Value
        }

        logger.debug(String.format("Loaded %d allow bald player template settings.", allowBald.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            allowBald.clear();
            load();
        }
    }
}
