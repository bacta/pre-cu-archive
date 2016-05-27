package com.ocdsoft.bacta.swg.server.game.service.data.faction;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.service.data.SharedFileLoader;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/30/14.
 */
@Singleton
public final class FactionRank implements SharedFileLoader {
    private static final String dataTableName = "datatables/faction/rank.iff";
    private static final Logger logger = LoggerFactory.getLogger(FactionRank.class);

    private final TIntObjectMap<FactionRankInfo> factionRanks = new TIntObjectHashMap<>();

    private final DataTableManager dataTableManager;

    @Inject
    public FactionRank(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * Gets a {@link FactionRankInfo} by index.
     *
     * @param index The index to retrieve.
     * @return Returns null if index not found.
     */
    public final FactionRankInfo getFactionRankInfo(int index) {
        return factionRanks.get(index);
    }

    /**
     * Gets a {@link FactionRankInfo} by name.
     * Names are like: lance_corporal, staff_sergeant, etc.
     *
     * @param name The name to search for.
     * @return {@link FactionRankInfo} if a name corresponds
     * in the map. Otherwise, null.
     */
    public final FactionRankInfo getFactionRankInfoByName(final String name) {
        TIntObjectIterator<FactionRankInfo> iterator = factionRanks.iterator();

        while (iterator.hasNext()) {
            final FactionRankInfo info = iterator.value();

            if (info.name.equals(name))
                return info;
        }

        return null;
    }

    private void load() {
        logger.trace("Loading faction ranks.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            final FactionRankInfo factionRankInfo = new FactionRankInfo(dataTable, row);
            factionRanks.put(factionRankInfo.getIndex(), factionRankInfo);
        }

        dataTableManager.close(dataTableName);

        logger.debug(String.format("Loaded %d faction ranks.", factionRanks.size()));
    }

    @Override
    public void reload() {
        synchronized (this) {
            factionRanks.clear();
            load();
        }
    }

    public final class FactionRankInfo {
        @Getter
        private final int index;
        @Getter
        private final String name;
        @Getter
        private final int cost;
        @Getter
        private final int delegateRatioFrom;
        @Getter
        private final int delegateRatioTo;

        public FactionRankInfo(final DataTable dataTable, final int row) {
            this.index = dataTable.getIntValue("INDEX", row);
            this.name = dataTable.getStringValue("NAME", row);
            this.cost = dataTable.getIntValue("COST", row);
            this.delegateRatioFrom = dataTable.getIntValue("DELEGATE_RATIO_FROM", row);
            this.delegateRatioTo = dataTable.getIntValue("DELEGATE_RATIO_TO", row);
        }
    }
}
