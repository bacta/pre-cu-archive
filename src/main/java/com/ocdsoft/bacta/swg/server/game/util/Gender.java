package com.ocdsoft.bacta.swg.server.game.util;


/**
 * Created by kburkhardt on 3/28/14.
 */
public enum Gender {
    MALE, FEMALE, Gender;

    public static Gender parseGender(String templateString) {
        final String gender = templateString.substring(templateString.lastIndexOf("_") + 1, templateString.indexOf(".iff"));
        return Gender.valueOf(gender.toUpperCase());
    }
}
