package com.ocdsoft.bacta.swg.server.game.group;

import com.ocdsoft.bacta.swg.shared.localization.StringId;

/**
 * Created by crush on 5/28/2016.
 * <p>
 * Just a class to give some type safety and maintainability to group {@link com.ocdsoft.bacta.swg.shared.localization.StringId}
 * references.
 */
public final class GroupStringId {
    public static final StringId MUST_BE_LEADER = new StringId("group", "must_be_leader");
    public static final StringId FULL = new StringId("group", "full");
    public static final StringId ALREADY_GROUPED = new StringId("group", "already_grouped");
    public static final StringId CONSIDERING_YOUR_GROUP = new StringId("group", "considering_your_group");
    public static final StringId CONSIDERING_OTHER_GROUP = new StringId("group", "considering_other_group");
    public static final StringId INVITE_LEADER = new StringId("group", "invite_leader");
    public static final StringId INVITE_TARGET = new StringId("group", "invite_target");
    public static final StringId INVITE_NO_TARGET_SELF = new StringId("group", "invite_no_target_self");
    public static final StringId MUST_BE_INVITED = new StringId("group", "must_be_invited");
    public static final StringId DECLINE_SELF = new StringId("group", "decline_self");
    public static final StringId DECLINE_LEADER = new StringId("group", "decline_leader");
    public static final StringId UNINVITE_SELF = new StringId("group", "uninvite_self");
    public static final StringId UNINVITE_TARGET = new StringId("group", "uninvite_target");
    public static final StringId UNINVITE_NOT_INVITED = new StringId("group", "uninvite_not_invited");
    public static final StringId UNINVITE_NO_TARGET_SELF = new StringId("group", "uninvite_no_target_self");
    public static final StringId NEW_LEADER = new StringId("group", "new_leader");
    public static final StringId NEW_MASTER_LOOTER = new StringId("group", "new_master_looter");
    public static final StringId FORMED = new StringId("group", "formed_self");
    public static final StringId JOIN = new StringId("group", "joined_self");
    public static final StringId DISBANDED = new StringId("group", "disbanded");
    public static final StringId REMOVED = new StringId("group", "removed");
    public static final StringId MUST_EJECT_FIRST = new StringId("group", "must_eject_first");
    public static final StringId ONLY_PLAYERS_IN_SPACE = new StringId("group", "only_players_in_space");
    public static final StringId NO_DISBAND_OWNER = new StringId("group", "no_disband_owner");
    public static final StringId NO_KICK_FROM_SHIP_OWNER = new StringId("group", "no_kick_from_ship_owner");
    public static final StringId BEASTS_CANT_JOIN = new StringId("group", "beasts_cant_join");
    public static final StringId EXISTING_GROUP_NOT_FOUND = new StringId("group", "existing_group_not_found");
    public static final StringId JOIN_INVITER_NOT_LEADER = new StringId("group", "join_inviter_not_leader");
    public static final StringId JOIN_FULL = new StringId("group", "join_full");
    public static final StringId INVITE_IN_COMBAT = new StringId("group", "invite_in_combat");
    public static final StringId INVITE_TARGET_IN_COMBAT = new StringId("group", "invite_target_in_combat");
    public static final StringId JOIN_IN_COMBAT = new StringId("group", "join_in_combat");
    public static final StringId JOIN_LEADER_IN_COMBAT = new StringId("group", "join_leader_in_combat");
}
