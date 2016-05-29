package com.ocdsoft.bacta.swg.server.chat;

/**
 * Created by crush on 5/28/2016.
 */
public final class ChatRoomAttributes {
    public static final int PRIVATE = 0x01;
    public static final int MODERATED = 0x02;
    public static final int PERSISTENT = 0x04;
    public static final int LOCAL_WORLD = 0x10;
    public static final int LOCAL_GAME = 0x20;

    public static boolean isSet(final int attributes, final int attributeType) {
        return (attributes & attributeType) != 0;
    }

    public static int setAttribute(final int attributes, final int attributeType) {
        return attributes | attributeType;
    }

    public static int unsetAttribute(final int attributes, final int attributeType) {
        return attributes & ~(attributeType);
    }
}
