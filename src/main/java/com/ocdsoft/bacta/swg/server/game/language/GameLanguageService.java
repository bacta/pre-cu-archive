package com.ocdsoft.bacta.swg.server.game.language;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.template.shared.SharedCreatureObjectTemplate;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import com.ocdsoft.bacta.swg.shared.localization.LocalizationManager;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by crush on 6/2/2016.
 */
@Singleton
public final class GameLanguageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameLanguageService.class);
    private static final String gameLanguageFile = "datatables/game_language/game_language.iff";
    private static final int MINIMUM_ABBREVIATION_LENGTH = 5;
    private static final String BASIC_LANGUAGE_NAME = "basic";
    private static final String SHYRIIWOOK_LANGUAGE_NAME = "shyriiwook";
    private static final String[] alphabet = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private Map<String, LanguageInfo> gameLanguages;

    @Inject
    public GameLanguageService(final DataTableManager dataTableManager,
                               final LocalizationManager localizationManager) {

        loadLanguages(dataTableManager.getTable(gameLanguageFile, true), localizationManager);
    }

    private void loadLanguages(final DataTable dataTable,
                               final LocalizationManager localizationManager) {
        final int rowCount = dataTable.getNumRows();
        final Map<String, LanguageInfo> gameLanguages = new HashMap<>(rowCount);

        for (int index = 0; index < rowCount; ++index) {
            final int languageId = ++index;
            final String stringIdName = dataTable.getStringValue("stringId", index);
            final String localizedName = new StringId("game_language", stringIdName).localize(localizationManager);
            final boolean audible = dataTable.getIntValue("audible", index) != 0;
            final String speakSkillModName = dataTable.getStringValue("speakSkillModName", index);
            final String comprehendskillModName = dataTable.getStringValue("comprehendSkillModName", index);

            final List<String> languageAlphabet = new ArrayList<>(alphabet.length);

            for (final String letter : alphabet)
                languageAlphabet.add(dataTable.getStringValue(letter, index));

            final LanguageInfo languageInfo = new LanguageInfo(
                    languageId,
                    localizedName,
                    languageAlphabet,
                    audible,
                    speakSkillModName,
                    comprehendskillModName,
                    stringIdName);

            gameLanguages.put(languageInfo.getStringIdName(), languageInfo);
        }

        LOGGER.debug("Loaded {} game languages.", gameLanguages.size());
        this.gameLanguages = ImmutableMap.copyOf(gameLanguages);
    }

    public boolean isLanguageValid(final String languageName) {
        return gameLanguages.containsKey(languageName);
    }

    public boolean isLanguageValid(final int languageId) {
        return gameLanguages.values().stream().anyMatch(language -> language.getId() == languageId);
    }

    public boolean isLanguageAbbreviationValid(final String abbreviation) {
        return getLanguageIdForAbbreviation(abbreviation) != -1;
    }

    public int getLanguageIdForAbbreviation(final String abbreviation) {
        final String lowerAbbreviation = abbreviation.toLowerCase();
        final int abbreviationLength = lowerAbbreviation.length();

        int languageId = -1;
        boolean result;
        for (final LanguageInfo languageInfo : gameLanguages.values()) {
            final String stringIdName = languageInfo.getStringIdName();
            if ((abbreviationLength >= MINIMUM_ABBREVIATION_LENGTH || abbreviationLength == stringIdName.length()) &&
                    abbreviationLength <= stringIdName.length()) {

                result = true;

                for (int i = 0; i < abbreviationLength; ++i) {
                    if (lowerAbbreviation.charAt(i) != stringIdName.charAt(i)) {
                        result = false;
                        break;
                    }
                }

                if (result) {
                    languageId = languageInfo.getId();
                    break;
                }
            }
        }

        return languageId;
    }

    public String getLanguageSpeakSkillModName(final int languageId) {
        final String languageName = getLanguageNameById(languageId);
        final LanguageInfo languageInfo = gameLanguages.get(languageName);
        return languageInfo != null ? languageInfo.getSpeakSkillModName() : "";
    }

    public String getLanguageComprehendSkillModName(final int languageId) {
        final String languageName = getLanguageNameById(languageId);
        final LanguageInfo languageInfo = gameLanguages.get(languageName);
        return languageInfo != null ? languageInfo.getComprehendSkillModName() : "";
    }

    public int getLanguageIdByName(final String languageName) {
        final LanguageInfo languageInfo = gameLanguages.get(languageName);
        return languageInfo != null ? languageInfo.getId() : 0;
    }

    public boolean isLanguageAudible(final int languageId) {
        final String langaugeName = getLanguageNameById(languageId);
        final LanguageInfo languageInfo = gameLanguages.get(langaugeName);
        return languageInfo != null && languageInfo.isAudible();
    }

    public String getLanguageNameById(final int languageId) {
        for (final LanguageInfo languageInfo : gameLanguages.values()) {
            if (languageInfo.getId() == languageId)
                return languageInfo.getStringIdName();
        }

        return "";
    }

    public int getBasicLanguageId() {
        return getLanguageIdByName(BASIC_LANGUAGE_NAME);
    }

    public int getStartingLanguage(final SharedCreatureObjectTemplate.Species species) {
        if (SharedCreatureObjectTemplate.Species.SP_wookiee.equals(species))
            return getLanguageIdByName(SHYRIIWOOK_LANGUAGE_NAME);

        return getBasicLanguageId();
    }
}
