package com.ocdsoft.bacta.swg.precu.object.matchmaking;


import com.ocdsoft.bacta.engine.lang.UnicodeString;

import java.util.Set;

/**
 * Created by crush on 8/15/2014.
 */
public class LfgCharacterSearchResultData {
    private long characterId;
    private UnicodeString characterName;
    //private SharedCreatureObjectTemplate:Species species;
    //private Profession profession;
    private short level;
    private int faction;
    private String guildName;
    private String guildAbbrev;
    private long groupId;
    private String locationPlanet;
    private String locationRegion;
    private String locationPlayerCity;
    private Set<String> ctsSourceGalaxy;
    //private BitArray characterInterests;
}
