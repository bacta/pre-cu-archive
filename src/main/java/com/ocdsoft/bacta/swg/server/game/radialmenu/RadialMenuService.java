package com.ocdsoft.bacta.swg.server.game.radialmenu;

import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TObjectShortHashMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 5/30/2016.
 * <p>
 * Manages manipulating radial menus for objects.
 */
public final class RadialMenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadialMenuService.class);
    private static final String DATATABLE_PATH = "datatables/player/radial_menu.iff";
    private static final int COL_CAPTION = 0;
    private static final int COL_RANGE = 1;
    private static final int COL_COMMAND_NAME = 2;
    private static final int COL_USE_RADIAL_TARGET = 3;

    private final TShortObjectMap<RadialMenuInfo> indexLookup;
    private final TObjectShortMap<String> nameLookup;

    public RadialMenuService(final DataTableManager dataTableManager) {
        indexLookup = new TShortObjectHashMap<>();
        nameLookup = new TObjectShortHashMap<>();

        final DataTable dataTable = dataTableManager.getTable(DATATABLE_PATH, true);

        loadDataTable(dataTable);
    }

    public void loadDataTable(final DataTable dataTable) {
        final int rowCount = dataTable.getNumRows();

        for (short i = 0; i < rowCount; ++i) {
            final String caption = dataTable.getStringValue(COL_CAPTION, i);

            LOGGER.debug("Loading radial menu info with caption {}", caption);

            final RadialMenuInfo info = new RadialMenuInfo(
                    dataTable.getFloatValue(COL_RANGE, i),
                    dataTable.getStringValue(COL_COMMAND_NAME, i),
                    dataTable.getIntValue(COL_USE_RADIAL_TARGET, i) != 0);

            indexLookup.put(i, info);
            nameLookup.put(caption, i);
        }
    }

    public short getTypeByName(final String typeName) {
        if (nameLookup.containsKey(typeName))
            return nameLookup.get(typeName);

        return 0;
    }
}
