package com.ocdsoft.bacta.swg.server.util;

import gnu.trove.list.array.TIntArrayList;
import org.junit.Test;

/**
 * Created by kyle on 4/23/2016.
 */
public class DistanceUtilTest {
    @Test
    public void shouldGrowAndFillTIntList() {
        TIntArrayList list = new TIntArrayList(0);

        if (5 >= list.size()) {
            for (int i = 0, size = list.size() - 1; i < 5 - size; ++i) {
                list.add(0);
            }
        }

        list.set(5, 10);

        for (int v : list.toArray()) {
            System.out.println(v);
        }
    }
}