package com.ocdsoft.bacta.swg.shared.chat;

/**
 * Created by crush on 5/23/2016.
 */
public enum PersistentMessageStatus {
    UNKNOWN('\0'),
    NEW('N'),
    UNREAD('U'),
    READ('R'),
    TRASH('T'),
    DELETED('D');

    public final char value;

    PersistentMessageStatus(final char value) {
        this.value = value;
    }

    public static PersistentMessageStatus from(final char value) {
        switch (value) {
            case 'N':
                return NEW;
            case 'U':
                return UNREAD;
            case 'R':
                return READ;
            case 'T':
                return TRASH;
            case 'D':
                return DELETED;
            default:
                return UNKNOWN;
        }
    }
}
