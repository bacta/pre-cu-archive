package com.ocdsoft.bacta.swg.server.game.object.universe.planet;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.server.game.object.universe.UniverseObject;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 * <p>
 * A pool of resource found on a planet.
 */
public class PlanetObject extends UniverseObject {
    @Inject
    public PlanetObject(final ObjectTemplateList objectTemplateList,
                        final SlotIdManager slotIdManager,
                        final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);

    }
}
