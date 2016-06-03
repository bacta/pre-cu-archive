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
public final class AttributeLimits implements SharedFileLoader {
    private static final String dataTableName = "datatables/creation/attribute_limits.iff";
    private static final Logger logger = LoggerFactory.getLogger(AttributeLimits.class);

    private final Map<String, AttributeLimitInfo> attributeLimits = new HashMap<>();

    private final DataTableManager dataTableManager;

    @Inject
    public AttributeLimits(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }
    /**
     * Gets an {@link AttributeLimitInfo} object for
     * the specified player template. <i>The player template should only be the file name, and not the fullpath.</i>
     * <p/>
     * i.e. bothan_female.
     *
     * @param playerTemplate The player template to use as a key. This should be the filename, not the full path. i.e. bothan_female
     * @return Returns an {@link AttributeLimitInfo} object
     * if a record corresponding to the key is found. Otherwise, null.
     */
    public AttributeLimitInfo getAttributeLimits(final String playerTemplate) {
        return attributeLimits.get(playerTemplate);
    }

    private void load() {
        logger.trace("Loading attribute limits.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            final AttributeLimitInfo limitInfo = new AttributeLimitInfo(dataTable, row);
            attributeLimits.put(limitInfo.maleTemplate, limitInfo);
            attributeLimits.put(limitInfo.femaleTemplate, limitInfo);
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d profession mods.", attributeLimits.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            attributeLimits.clear();
            load();
        }
    }

    public static final class AttributeLimitInfo {
        @Getter
        private final String maleTemplate;
        @Getter
        private final String femaleTemplate;
        @Getter
        private final int minHealth;
        @Getter
        private final int maxHealth;
        @Getter
        private final int minStrength;
        @Getter
        private final int maxStrength;
        @Getter
        private final int minConstitution;
        @Getter
        private final int maxConstitution;
        @Getter
        private final int minAction;
        @Getter
        private final int maxAction;
        @Getter
        private final int minQuickness;
        @Getter
        private final int maxQuickness;
        @Getter
        private final int minStamina;
        @Getter
        private final int maxStamina;
        @Getter
        private final int minMind;
        @Getter
        private final int maxMind;
        @Getter
        private final int minFocus;
        @Getter
        private final int maxFocus;
        @Getter
        private final int minWillpower;
        @Getter
        private final int maxWillpower;
        @Getter
        private final int total;

        public AttributeLimitInfo(final DataTable dataTable, final int row) {
            this.maleTemplate = dataTable.getStringValue("male_template", row);
            this.femaleTemplate = dataTable.getStringValue("female_template", row);
            this.minHealth = dataTable.getIntValue("min_health", row);
            this.maxHealth = dataTable.getIntValue("max_health", row);
            this.minStrength = dataTable.getIntValue("min_strength", row);
            this.maxStrength = dataTable.getIntValue("max_strength", row);
            this.minConstitution = dataTable.getIntValue("min_constitution", row);
            this.maxConstitution = dataTable.getIntValue("max_constitution", row);
            this.minAction = dataTable.getIntValue("min_action", row);
            this.maxAction = dataTable.getIntValue("max_action", row);
            this.minQuickness = dataTable.getIntValue("min_quickness", row);
            this.maxQuickness = dataTable.getIntValue("max_quickness", row);
            this.minStamina = dataTable.getIntValue("min_stamina", row);
            this.maxStamina = dataTable.getIntValue("max_stamina", row);
            this.minMind = dataTable.getIntValue("min_mind", row);
            this.maxMind = dataTable.getIntValue("max_mind", row);
            this.minFocus = dataTable.getIntValue("min_focus", row);
            this.maxFocus = dataTable.getIntValue("max_focus", row);
            this.minWillpower = dataTable.getIntValue("min_willpower", row);
            this.maxWillpower = dataTable.getIntValue("max_willpower", row);
            this.total = dataTable.getIntValue("total", row);
        }
    }
}
