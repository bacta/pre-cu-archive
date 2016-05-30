package com.ocdsoft.bacta.swg.server.game.group;

import com.ocdsoft.bacta.swg.server.game.object.matchmaking.LfgCharacterData;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by crush on 5/28/2016.
 */
@Getter
@AllArgsConstructor
public final class GroupMemberParam {
    private final long id;
    private final String name;
    private final int difficulty;
    private final LfgCharacterData.Profession profession;
    private final boolean isPc;
    private final long shipId;
    private final boolean shipIsPob;
    private final boolean ownsPob;
}
