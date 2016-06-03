package com.ocdsoft.bacta.swg.server.game.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by crush on 6/2/2016.
 */
@Getter
@AllArgsConstructor
public final class LanguageInfo {
    private final int id;
    private final String localizedName;
    private final List<String> alphabet;
    private final boolean audible;
    private final String speakSkillModName;
    private final String comprehendSkillModName;
    private final String stringIdName;
}
