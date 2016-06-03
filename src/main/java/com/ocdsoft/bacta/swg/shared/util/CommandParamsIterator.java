package com.ocdsoft.bacta.swg.shared.util;

import com.ocdsoft.bacta.swg.shared.math.Vector;

/**
 * Created by crush on 6/1/2016.
 */
public final class CommandParamsIterator {
    private final String params;
    private int position;

    public CommandParamsIterator(final String params) {
        this.params = params;
    }

    public boolean isAtEnd() {
        return position > params.length() - 1;
    }

    /**
     * Gets the next token in the string that is separated by a whitespace.
     *
     * @return string value.
     * @throws StringIndexOutOfBoundsException If there are no more tokens in the string.
     */
    public String getString() {
        return getStringSeparatedBy(' ');
    }

    /**
     * Gets a string token separated by the given character.
     *
     * @param separator The separator character.
     * @return string value.
     * @throws StringIndexOutOfBoundsException If there are no more tokens in the string.
     */
    public String getStringSeparatedBy(final char separator) {
        skipWhiteSpace();

        final int endIndex = params.indexOf(separator, position);
        final String value;

        if (endIndex == -1) {
            value = params;
            position = params.length();
        } else {
            value = params.substring(position, endIndex);
            position = endIndex + 1;
        }

        skipWhiteSpace();
        return value;
    }

    /**
     * Gets the remaining string, but will trim the whitespace off the front and back.
     * @return The rest of the String, trimmed.
     * @see #getRemainingString()
     */
    public String getRemainingStringTrimmed() {
        return internalGetRemainingString(true);
    }

    /**
     * Gets exactly whats left in the underlying string.
     *
     * @return The rest of the String.
     * @see #getRemainingStringTrimmed()
     */
    public String getRemainingString() {
        return internalGetRemainingString(false);
    }

    /**
     * Attempts to get everything in the string until a null character is encountered, at which it will return the
     * string at that position. There may be more in the string after the null character.
     *
     * @return
     */
    public String getRemainingStringToNull() {
        final StringBuilder sb = new StringBuilder(params.length() - position);

        for (int size = params.length(); position < size; ++position) {
            final char c = params.charAt(position);

            if (c == '\0') {
                ++position; //increment position one more to skip the null byte.
                break;
            }

            sb.append(c);
        }

        return sb.toString();
    }

    private String internalGetRemainingString(final boolean trim) {
        final String result;
        if (!trim) {
            result = params.substring(position);
        } else {
            //NOTE: This only matches on space, and not all whitespace. Might want to fix eventually.
            final int lastIndex = params.lastIndexOf(' ');

            if (lastIndex <= position)
                result = params.substring(position);
            else
                result = params.substring(position, lastIndex);
        }

        position = params.length();
        return result;
    }

    /**
     * Gets the next token in the string as a float.
     *
     * @return float value.
     * @throws NumberFormatException           If the next value is not a float.
     * @throws StringIndexOutOfBoundsException If there are no more tokens in the string.
     */
    public float getFloat() {
        skipWhiteSpace();

        final int endIndex = params.indexOf(' ', position);
        final float value;

        if (endIndex == -1) {
            value = Float.parseFloat(params.substring(position));
            position = params.length();
        } else {
            value = Float.parseFloat(params.substring(position, endIndex));
            position = endIndex + 1;
        }

        skipWhiteSpace();

        return value;
    }

    public int getInteger() {
        skipWhiteSpace();

        final int endIndex = params.indexOf(' ', position);
        final int value;

        if (endIndex == -1) {
            value = Integer.parseInt(params.substring(position));
            position = params.length();
        } else {
            value = Integer.parseInt(params.substring(position, endIndex));
            position = endIndex + 1;
        }

        skipWhiteSpace();

        return value;
    }

    public long getLong() {
        skipWhiteSpace();

        final int endIndex = params.indexOf(' ', position);
        final long value;

        if (endIndex == -1) {
            value = Long.parseLong(params.substring(position));
            position = params.length();
        } else {
            value = Long.parseLong(params.substring(position, endIndex));
            position = endIndex + 1;
        }

        skipWhiteSpace();

        return value;
    }

    public Vector getVector() {
        return new Vector(getFloat(), getFloat(), getFloat());
    }

    public void skipWhiteSpace() {
        for (final int size = params.length(); position < size; ++position)
            if (!Character.isWhitespace(params.charAt(position)))
                return;
    }
}