package com.ocdsoft.bacta.swg.server.game.object;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 */
public class StaticObject extends ServerObject {
    @Inject
    public StaticObject(final ObjectTemplateList objectTemplateList,
                        final SlotIdManager slotIdManager,
                        final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);
    }
}
