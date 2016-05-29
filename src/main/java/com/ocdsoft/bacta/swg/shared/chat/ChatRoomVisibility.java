package com.ocdsoft.bacta.swg.shared.chat;

/**
 * Created by crush on 5/28/2016.
 */
public enum ChatRoomVisibility {
    PUBLIC(0),
    PRIVATE(1);

    public final int value;

    ChatRoomVisibility(final int value) {
        this.value = value;
    }

    public static ChatRoomVisibility from(final int value) {
        return value == 1 ? PRIVATE : PUBLIC;
    }
}
