package com.ocdsoft.bacta.swg.server.game.object.universe;


import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.server.game.object.template.shared.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 9/3/2014.
 * <p>
 * A UniverseObject is an object that is global to the entire server
 * cluster.  UniverseObjects represent global data (such as Resource
 * Classes) or objects with no definite location (such as Resource Pools).
 */
public abstract class UniverseObject extends ServerObject {
    private static SharedObjectTemplate defaultSharedObjectTemplate; //gets set by a service at runtime.

    @Inject
    public UniverseObject(final ObjectTemplateList objectTemplateList,
                          final SlotIdManager slotIdManager,
                          final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);
    }
}
