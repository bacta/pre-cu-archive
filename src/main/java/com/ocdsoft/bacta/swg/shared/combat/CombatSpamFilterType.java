package com.ocdsoft.bacta.swg.shared.combat;

/**
 * Created by crush on 5/26/2016.
 */
public enum CombatSpamFilterType {
    ALL(0),
    SELF(1),
    GROUP(2),
    NONE(3);

    private static final CombatSpamFilterType[] values = values();

    public final int value;

    CombatSpamFilterType(final int value) {
        this.value = value;
    }

    public static final CombatSpamFilterType from(final int value) {
        if (value <= 0 || value > NONE.value)
            return NONE;

        return values[value];
    }
}
