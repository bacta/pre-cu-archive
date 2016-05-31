package com.ocdsoft.bacta.swg.shared.chat.messages;

import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Created by crush on 5/20/2016.
 */
public enum ChatResult {
    SUCCESS(0),
    TIMEOUT(1), //ChatApi timedout...
    DUPLICATE_LOGIN(2), //Tried to connect, but already connected!?
    SRC_AVATAR_DOESNT_EXIST(3),
    DST_AVATAR_DOESNT_EXIST(4),
    ADDRESS_DOESNT_EXIST(5), //Address doesn't exist...
    ADDRESS_NOT_ROOM(6), //Address is not a room...
    ADDRESS_NOT_AID(7),
    FRIEND_NOT_FOUND(8), //Tried to remove a friend, but they were not a friend.
    ROOM_UNKNOWN_FAILURE(9), //Unknown failure.
    ROOM_SRC_NOT_IN_ROOM(10), //Generic result where an avatar is the source of an action, but is not present in room.
    ROOM_DST_NOT_IN_ROOM(11),
    ROOM_BANNED_AVATAR(12), //Tried to join a room, but were banned.
    ROOM_PRIVATE_ROOM(13), //Tried to join a room, but it was private.
    ROOM_MODERATED_ROOM(14), //Tried to talk in a room, but it was moderated.
    ROOM_NOT_IN_ROOM(15), //Tried to leave a room, but not in the room.
    ROOM_NO_PRIVILEGES(16), //Tried to perform an elevated action, but have no permissions to do so.
    DATABASE(17),
    CANNOT_GET_AVATAR_ID(18),
    CANNOT_GET_NODE_ID(19),
    CANNOT_GET_PMSG_ID(20),
    PMSG_NOT_FOUND(21),
    ROOM_MAX_AVATARS_REACHED(22), //Could not join the room becasue max number of avatars was reached.
    IGNORING(23), //Can't communicate because ignoring avatar.
    ROOM_ALREADY_EXISTS(24), //Tried to create a room, but the room already existed.
    NOTHING_TO_CONFIRM(25),
    DUPLICATE_FRIEND(26), //Tried to add a friend, but they were already a friend.
    IGNORE_NOT_FOUND(27), //Tried to remove an ignore, but they were not ignored.
    DUPLICATE_IGNORE(28), //Tried to add an ignore, but they were already ignored.
    DB_FAIL(29), //Failure reading or writing to the database.
    ROOM_DST_AVATAR_NOT_MODERATOR(30), //Tried to remove a moderator, but they were not a moderator.
    ROOM_DST_AVATAR_NOT_INVITED(31), //Tried to uninvite an avatar, but they were not invited.
    ROOM_DST_AVATAR_NOT_BANNED(32), //Tried to remove a ban, but the avatar was already not banned.
    ROOM_DUPLICATE_BAN(33), //Tried to add a ban, but they were already banned.
    ROOM_DUPLICATE_MODERATOR(34), //Tried to add a moderator, but they were already moderating.
    ROOM_DUPLICATE_INVITE(35), //Tried to invite an avatar, but they were already invited.
    ROOM_ALREADY_IN_ROOM(36), //Tried to join a room, but was already in the room.
    ROOM_PARENT_NON_PERSISTENT(37), //Could not make room persistent because parent is not persistent.
    ROOM_PARENT_BAD_NOTE_TYPE(38),
    NO_FAN_CLUB_HANDLE(39),

    CHAT_SERVER_UNAVAILABLE(1000000),
    WRONG_FACTION(1000001),
    INVALID_OBJECT(1000002),
    NO_GAME_SERVER(1000003),
    NOT_WARDEN(1000004),
    WRONG_GCW_REGION_DEFENDER_FACTION(1000005);


    private static final TIntObjectMap<ChatResult> reverseLookup;

    static {
        final ChatResult[] types = values();
        final int size = types.length;

        final TIntObjectMap<ChatResult> temporaryMap = new TIntObjectHashMap<>(size);

        for (final ChatResult type : types)
            temporaryMap.put(type.value, type);

        reverseLookup = TCollections.unmodifiableMap(temporaryMap);
    }

    public final int value;

    ChatResult(final int value) {
        this.value = value;
    }

    public static ChatResult from(final int value) {
        final ChatResult type = reverseLookup.get(value);

        if (type == null)
            throw new IllegalArgumentException("The provided value does not map to any ChatResult type.");

        return type;
    }
}
