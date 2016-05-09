package com.ocdsoft.bacta.swg.precu.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerStaticObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 */
public class StaticObject extends ServerObject {
    @Inject
    public StaticObject(final ObjectTemplateList objectTemplateList,
                        final SlotIdManager slotIdManager,
                        final ServerStaticObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);
    }
}
