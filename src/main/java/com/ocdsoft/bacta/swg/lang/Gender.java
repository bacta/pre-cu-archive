package com.ocdsoft.bacta.swg.lang;

/**
 * Created by kburkhardt on 3/28/14.
 */
public enum Gender {
    MALE, FEMALE;

    public static Gender parseGender(String templateString) {
        String gender = templateString.substring(templateString.lastIndexOf("_") + 1, templateString.indexOf(".iff"));
        return Gender.valueOf(gender.toUpperCase());
    }
}
