package com.ocdsoft.bacta.swg.server.object.universe.planet;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerPlanetObjectTemplate;
import com.ocdsoft.bacta.swg.server.object.universe.UniverseObject;
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
                        final ServerPlanetObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);

    }
}
