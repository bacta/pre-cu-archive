package com.ocdsoft.bacta.swg.server.game.util;

import com.ocdsoft.bacta.soe.util.SOECRC32;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kyle on 4/23/2016.
 */
public class DistanceUtilTest {
    @Test
    public void shouldGrowAndFillTIntList() {
        Assert.assertEquals("0x173b91c2", String.format("0x%x", SOECRC32.hashCode("28afefcc187a11dc888b001")));
    }
}