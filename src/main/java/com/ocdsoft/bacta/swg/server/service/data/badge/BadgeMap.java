package com.ocdsoft.bacta.swg.server.service.data.badge;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.service.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public final class BadgeMap implements SharedFileLoader {
    private static final String dataTableName = "datatables/badge/badge_map.iff";
    private static final Logger logger = LoggerFactory.getLogger(BadgeMap.class);

    private final TIntObjectMap<BadgeInfo> badges = new TIntObjectHashMap<>();
    private final DataTableManager dataTableManager;

    @Inject
    public BadgeMap(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    private void load() {
        logger.trace("Loading badges.");

        final DataTable badgeMap = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < badgeMap.getNumRows(); ++row) {
            final BadgeInfo badge = new BadgeInfo(badgeMap, row);
            badges.put(badge.getIndex(), badge);
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d badges.", badges.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            badges.clear();
            load();
        }
    }

    /**
     * Gets the badge corresponding to the index in the map.
     *
     * @param index Index of the corresponding badge.
     * @return Badge corresponding to index if it exists. Otherwise, null.
     */
    public BadgeInfo getBadge(int index) {
        return badges.get(index);
    }


    /**
     * Gets the string key associated with a badge index. Use this method if all you need is the key.
     *
     * @param index The index of the associated badge.
     * @return String key if the index exists. Otherwise null.
     */
    public String getBadgeKeyByIndex(int index) {
        final BadgeInfo badge = badges.get(index);
        return badge == null ? null : badge.getKey();
    }

    /**
     * Gets a badge based on its string key. For example, "count_5".
     *
     * @param key The string key for the badge.
     * @return Badge if the string key exists. Otherwise null.
     */
    public BadgeInfo getBadge(String key) {
        final TIntObjectIterator<BadgeInfo> iterator = badges.iterator();

        while (iterator.hasNext()) {
            final BadgeInfo badge = iterator.value();

            if (badge.getKey().equals(key))
                return badge;
        }

        return null;
    }

    /**
     * Returns all badges in the map that match a specified category.
     *
     * @param category A category number.
     * @return Collection of badges matching the category. Collection is an {@link java.util.ArrayList}.
     * If no badges matched the category, then an empty list is returned.
     */
    public Collection<BadgeInfo> getBadgesByCategory(int category) {
        final TIntObjectIterator<BadgeInfo> iterator = badges.iterator();
        final Collection<BadgeInfo> collection = new ArrayList<BadgeInfo>();

        while (iterator.hasNext()) {
            final BadgeInfo badge = iterator.value();

            if (badge.getCategory() == category)
                collection.add(badge);
        }

        return collection;
    }

    public final class BadgeInfo {
        @Getter
        private final int index;
        @Getter
        private final String key;
        @Getter
        private final String music;
        @Getter
        private final int category;
        @Getter
        private final int show;
        @Getter
        private final String type;

        public BadgeInfo(final DataTable dataTable, int row) {
            this.index = dataTable.getIntValue("INDEX", row);
            this.key = dataTable.getStringValue("KEY", row);
            this.music = dataTable.getStringValue("MUSIC", row);
            this.category = dataTable.getIntValue("CATEGORY", row);
            this.show = dataTable.getIntValue("SHOW", row);
            this.type = dataTable.getStringValue("TYPE", row);
        }
    }
}
