package com.ocdsoft.bacta.swg.server.game.name.generator;

import com.google.common.base.CharMatcher;
import com.ocdsoft.bacta.swg.server.game.name.NameService;
import com.ocdsoft.bacta.swg.server.game.util.Gender;
import com.ocdsoft.bacta.swg.server.game.util.Race;
import org.apache.commons.lang.WordUtils;

/**
 * Created by Kyle on 8/17/2014.
 */
public class CreatureNameGenerator extends NameGenerator {

    @Override
    public String validateName(final String name, final Race race, final Gender gender) {

        // No Digits
        if (CharMatcher.DIGIT.countIn(name) > 0) {
            return NameService.NAME_DECLINED_NUMBER;
        }

        String firstName = name.contains(" ") ? name.substring(0, name.indexOf(" ")) : name;
        String lastName = name.contains(" ") ? name.substring(name.indexOf(" ") + 1) : "";

        if (firstName.length() < 3 || firstName.length() > 15) {
            return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
        }

        if (lastName.length() != 0 && (lastName.length() < 3 || lastName.length() > 20)) {
            return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
        }

        // Only Upper case in the first letter of each word
        if (CharMatcher.JAVA_UPPER_CASE.countIn(firstName.substring(1)) > 0 || (lastName.length() > 1 && CharMatcher.JAVA_UPPER_CASE.countIn(lastName.substring(1)) > 0)) {
            return NameService.NAME_DECLINED_SYNTAX;
        }

        if (CharMatcher.WHITESPACE.countIn(name) > 1) {
            return NameService.NAME_DECLINED_SYNTAX;
        }

        // Wookies cant have last names
        if (race == Race.WOOKIE && lastName.length() != 0) {
            return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
        }

        // Check special characters
        String specialChars = name.replaceAll("[a-zA-Z ]", "");
        if (race == Race.HUMAN || race == Race.TWILEK) {

            if (specialChars.length() > 0 && (!specialChars.equals("-") || !specialChars.equals("'"))) {
                return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
            }

        } else if (race == Race.MONCAL) {

            if (specialChars.length() > 0 && !specialChars.equals("-")) {
                return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
            }

        } else if (race == Race.BOTHAN) {
            String firstNameSpecials = firstName.replaceAll("[a-zA-Z ]", "");
            String lastNameSpecials = lastName.replaceAll("[a-zA-Z ]", "");
            if (firstNameSpecials.length() > 0 || (lastNameSpecials.length() > 0 && !specialChars.equals("'"))) {
                return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
            }

        } else {
            if (specialChars.length() != 0) {
                return NameService.NAME_DECLINED_RACIALLY_INAPPROPRIATE;
            }
        }

        return NameService.NAME_APPROVED;
    }

    @Override
    public String createName(final Race race, final Gender gender) {

        String firstName = WordUtils.capitalize(generateRandomCharacterSet(3, 9));
        String lastName = WordUtils.capitalize(generateRandomCharacterSet(3, 13));

        return firstName + " " + lastName;
    }

}
