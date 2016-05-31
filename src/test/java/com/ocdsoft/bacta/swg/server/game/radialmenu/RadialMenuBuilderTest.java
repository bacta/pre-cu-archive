package com.ocdsoft.bacta.swg.server.game.radialmenu;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.ocdsoft.bacta.swg.server.game.radialmenu.RadialMenuBuilder.RadialSubMenuBuilder;

/**
 * Created by crush on 5/30/2016.
 */
public class RadialMenuBuilderTest {
    @Test
    public void shouldProduceMenu() {
        final List<ObjectMenuRequestData> menu = RadialMenuBuilder.newBuilder()
                .root((short) 100, "ROTATE", true)
                .item((short) 101, "ROTATE_LEFT", true)
                .item((short) 102, "ROTATE_RIGHT", true)
                .root((short) 110, "PICKUP", true)
                .root((short) 126, "LOOT", true)
                .item((short) 127, "LOOT_ALL", true)
                .build();

        final byte firstParentId = menu.get(0).getId();

        Assert.assertEquals(1, menu.get(0).getId());
        Assert.assertEquals(2, menu.get(1).getId());
        Assert.assertEquals(3, menu.get(2).getId());
        Assert.assertEquals(4, menu.get(3).getId());
        Assert.assertEquals(5, menu.get(4).getId());
        Assert.assertEquals(6, menu.get(5).getId());

        Assert.assertEquals(6, menu.size());
        Assert.assertEquals(3, menu.stream().filter(o -> o.getParentId() == 0).count());
        Assert.assertEquals(3, menu.stream().filter(o -> o.getParentId() != 0).count());
        Assert.assertEquals(2, menu.stream().filter(o -> o.getParentId() == firstParentId).count());
    }

    @Test
    public void shouldProduceMenuDisjointed() {
        final RadialMenuBuilder builder = RadialMenuBuilder.newBuilder();

        final RadialSubMenuBuilder rotateMenu = builder
                .root((short) 100, "ROTATE", true);

        final RadialSubMenuBuilder pickupMenu = builder
                .root((short) 110, "PICKUP", true);

        rotateMenu
                .item((short) 101, "ROTATE_LEFT", true)
                .item((short) 102, "ROTATE_RIGHT", true)
                .item((short) 103, "ROTATE_UP", true);

        final List<ObjectMenuRequestData> menu = builder.build();

        Assert.assertEquals(1, menu.get(0).getId());
        Assert.assertEquals(2, menu.get(1).getId());
        Assert.assertEquals(3, menu.get(2).getId());
        Assert.assertEquals(4, menu.get(3).getId());
        Assert.assertEquals(5, menu.get(4).getId());

        Assert.assertEquals("ROTATE", menu.get(0).getLabel());
        Assert.assertEquals("PICKUP", menu.get(1).getLabel());
        Assert.assertEquals("ROTATE_LEFT", menu.get(2).getLabel());
        Assert.assertEquals("ROTATE_RIGHT", menu.get(3).getLabel());
        Assert.assertEquals("ROTATE_UP", menu.get(4).getLabel());

        Assert.assertEquals(5, menu.size());
        Assert.assertEquals(2, menu.stream().filter(o -> o.getParentId() == 0).count());
        Assert.assertEquals(3, menu.stream().filter(o -> o.getParentId() != 0).count());
    }
}
