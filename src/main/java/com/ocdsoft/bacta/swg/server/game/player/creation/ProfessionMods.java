package com.ocdsoft.bacta.swg.server.game.player.creation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.service.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class ProfessionMods implements SharedFileLoader {
    private static final String dataTableName = "datatables/creation/profession_mods.iff";
    private static final Logger logger = LoggerFactory.getLogger(ProfessionMods.class);

    private Map<String, ProfessionModInfo> professionMods = new HashMap<>();
    private final DataTableManager dataTableManager;

    @Inject
    public ProfessionMods(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * Gets a {@link ProfessionModInfo} object for the
     * specified profession.
     *
     * @param profession The profession is a string. i.e. combat_marksman, crafting_artisan, etc.
     * @return {@link ProfessionModInfo} if the key exists.
     * Otherwise, null.
     */
    public ProfessionModInfo getProfessionModInfo(final String profession) {
        return professionMods.get(profession);
    }

    private void load() {
        logger.trace("Loading profession mods.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            final ProfessionModInfo modInfo = new ProfessionModInfo(dataTable, row);
            professionMods.put(modInfo.profession, modInfo);
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d profession mods.", professionMods.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            professionMods.clear();
            load();
        }
    }

    public static final class ProfessionModInfo {
        @Getter
        private final String profession;
        @Getter
        private final Collection<Integer> attributes = new ArrayList<>(9);

        public ProfessionModInfo(final DataTable dataTable, final int row) {
            profession = dataTable.getStringValue("profession", row);
            attributes.add(dataTable.getIntValue(1, row));
            attributes.add(dataTable.getIntValue(2, row));
            attributes.add(dataTable.getIntValue(3, row));
            attributes.add(dataTable.getIntValue(4, row));
            attributes.add(dataTable.getIntValue(5, row));
            attributes.add(dataTable.getIntValue(6, row));
            attributes.add(dataTable.getIntValue(7, row));
            attributes.add(dataTable.getIntValue(8, row));
            attributes.add(dataTable.getIntValue(9, row));
        }
    }
}
