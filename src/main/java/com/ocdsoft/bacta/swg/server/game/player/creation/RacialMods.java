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
public class RacialMods implements SharedFileLoader {
    private static final String dataTableName = "datatables/creation/racial_mods.iff";
    private static final Logger logger = LoggerFactory.getLogger(RacialMods.class);

    private final Map<String, RacialModInfo> racialMods = new HashMap<>();

    private final DataTableManager dataTableManager;

    @Inject
    public RacialMods(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * Gets a {@link RacialModInfo} object for the given
     * player template.
     *
     * @param playerTemplate Player template is a string. It's a file name, not a file path. i.e. bothan_male.
     * @return {@link RacialModInfo} if key exists. Otherwise, null.
     */
    public RacialModInfo getRacialModInfo(final String playerTemplate) {
        return racialMods.get(playerTemplate);
    }

    private void load() {
        logger.trace("Loading racial mods.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            final RacialModInfo modInfo = new RacialModInfo(dataTable, row);
            racialMods.put(modInfo.maleTemplate, modInfo);
            racialMods.put(modInfo.femaleTemplate, modInfo);
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d racial mods.", racialMods.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            racialMods.clear();
            load();
        }
    }


    public static final class RacialModInfo {
        @Getter
        private final String maleTemplate;
        @Getter
        private final String femaleTemplate;
        @Getter
        private final int health;
        @Getter
        private final int strength;
        @Getter
        private final int constitution;
        @Getter
        private final int action;
        @Getter
        private final int quickness;
        @Getter
        private final int stamina;
        @Getter
        private final int mind;
        @Getter
        private final int focus;
        @Getter
        private final int willpower;

        public RacialModInfo(final DataTable dataTable, final int row) {
            this.maleTemplate = dataTable.getStringValue("male_template", row);
            this.femaleTemplate = dataTable.getStringValue("female_template", row);
            this.health = dataTable.getIntValue("health", row);
            this.strength = dataTable.getIntValue("strength", row);
            this.constitution = dataTable.getIntValue("constitution", row);
            this.action = dataTable.getIntValue("action", row);
            this.quickness = dataTable.getIntValue("quickness", row);
            this.stamina = dataTable.getIntValue("stamina", row);
            this.mind = dataTable.getIntValue("mind", row);
            this.focus = dataTable.getIntValue("focus", row);
            this.willpower = dataTable.getIntValue("willpower", row);
        }
    }
}
