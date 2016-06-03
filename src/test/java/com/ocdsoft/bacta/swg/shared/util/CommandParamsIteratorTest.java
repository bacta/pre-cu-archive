package com.ocdsoft.bacta.swg.shared.util;

import com.ocdsoft.bacta.swg.shared.math.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by crush on 6/1/2016.
 */
public class CommandParamsIteratorTest {

    @Test
    public void shouldGetString() {
        final String params = "   test 123456 Hello+World!   ";

        final CommandParamsIterator iterator = new CommandParamsIterator(params);
        final String string1 = iterator.getString();
        final String string2 = iterator.getString();
        final String string3 = iterator.getString();

        Assert.assertEquals("test", string1);
        Assert.assertEquals("123456", string2);
        Assert.assertEquals("Hello+World!", string3);
    }

    @Test
    public void shouldGetStringSeparatedBy() {
        final String params = " testing 1234567+Hello   ";

        final CommandParamsIterator iterator = new CommandParamsIterator(params);
        final String string1 = iterator.getStringSeparatedBy('+');
        final String string2 = iterator.getString();

        Assert.assertEquals("testing 1234567", string1);
        Assert.assertEquals("Hello", string2);
    }

    @Test
    public void shouldGetInteger() {
        final String params = "  594 383    354 29   193";

        final CommandParamsIterator iterator = new CommandParamsIterator(params);
        final int a = iterator.getInteger();
        final int b = iterator.getInteger();
        final int c = iterator.getInteger();
        final int d = iterator.getInteger();
        final int e = iterator.getInteger();

        Assert.assertEquals(594, a);
        Assert.assertEquals(383, b);
        Assert.assertEquals(354, c);
        Assert.assertEquals(29, d);
        Assert.assertEquals(193, e);
    }

    @Test
    public void shouldGetFloat() {
        final String params = "    -12403.394 -489.23 -211   300";

        final CommandParamsIterator iterator = new CommandParamsIterator(params);
        final float v = iterator.getFloat();
        final float x = iterator.getFloat();
        final float y = iterator.getFloat();
        final float z = iterator.getFloat();

        Assert.assertEquals(-12403.394, v, 0.001);
        Assert.assertEquals(-489.23, x, 0.001);
        Assert.assertEquals(-211, y, 0.001);
        Assert.assertEquals(300, z, 0.001);
    }

    @Test
    public void shouldGetVector() {
        final String params = " -4506.3 3.4 5934";

        final CommandParamsIterator iterator = new CommandParamsIterator(params);
        final Vector actual = iterator.getVector();
        final Vector expected = new Vector(-4506.3f, 3.4f, 5934f);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldGetRemainingStringToNull() {
        final String params = new String(new byte[]{'T', 'e', 's', 't', '\0', 'i', 'n', 'g'});

        final CommandParamsIterator iterator = new CommandParamsIterator(params);
        final String beforeNull = iterator.getRemainingStringToNull();
        final String afterNull = iterator.getRemainingStringTrimmed();

        Assert.assertEquals("Test", beforeNull);
        Assert.assertEquals("ing", afterNull);

        final CommandParamsIterator otherIterator = new CommandParamsIterator("Testing");

        Assert.assertEquals("Testing", otherIterator.getRemainingStringToNull());
    }
}
