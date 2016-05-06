package com.ocdsoft.bacta.swg.precu.service.data.language;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.datatable.DataTable;
import com.ocdsoft.bacta.swg.datatable.DataTableManager;
import com.ocdsoft.bacta.swg.precu.service.data.SharedFileLoader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public class GameLanguage implements SharedFileLoader {
    private static final String dataTableName = "datatables/game_language/game_language.iff";
    private static final Logger logger = LoggerFactory.getLogger(GameLanguage.class);

    private final Map<String, GameLanguageInfo> gameLanguage = new HashMap<>();
    private final DataTableManager dataTableManager;

    @Inject
    public GameLanguage(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * A {@link GameLanguageInfo} can be retrieved by
     * specifying either the speak skill, comprehension skill, or language name. i.e. language_basic_speak,
     * language_basic_comprehend, and basic will all return the object for the basic language.
     *
     * @param language The key representing the desired GameLanguageInfo.
     * @return {@link GameLanguageInfo} if key exists.
     * Otherwise, null.
     */
    public GameLanguageInfo getGameLanguageInfo(final String language) {
        return gameLanguage.get(language);
    }

    private void load() {
        logger.trace("Loading game language.");

        final DataTable dataTable = dataTableManager.getTable(dataTableName, true);

        for (int row = 0; row < dataTable.getNumRows(); ++row) {
            final GameLanguageInfo languageInfo = new GameLanguageInfo(dataTable, row);
            gameLanguage.put(languageInfo.speakSkillModName, languageInfo);
            gameLanguage.put(languageInfo.comprehendSkillModName, languageInfo);
            gameLanguage.put(languageInfo.stringId, languageInfo);
        }

        dataTableManager.close(dataTableName);

        logger.debug("Finished loading game language.");
    }

    @Override
    public void reload() {
        synchronized (this) {
            gameLanguage.clear();
            load();
        }
    }

    public static final class GameLanguageInfo {
        @Getter
        private final String speakSkillModName;
        @Getter
        private final String comprehendSkillModName;
        @Getter
        private final String stringId;
        @Getter
        private final int audible;

        private final String[] alphabet = new String[26];

        public GameLanguageInfo(final DataTable dataTable, final int row) {
            speakSkillModName = dataTable.getStringValue("speakSkillModName", row);
            comprehendSkillModName = dataTable.getStringValue("comprehendSkillModName", row);
            stringId = dataTable.getStringValue("stringId", row);

            for (int i = 0; i < alphabet.length; i++)
                alphabet[i] = dataTable.getStringValue(i + 3, row);

            audible = dataTable.getIntValue("audible", row);
        }
    }
}
