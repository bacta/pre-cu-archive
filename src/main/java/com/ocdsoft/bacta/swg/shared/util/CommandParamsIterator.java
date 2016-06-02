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

    public Vector getVector() {
        return new Vector(getFloat(), getFloat(), getFloat());
    }

    public void skipWhiteSpace() {
        for (final int size = params.length(); position < size; ++position)
            if (!Character.isWhitespace(params.charAt(position)))
                return;
    }
}