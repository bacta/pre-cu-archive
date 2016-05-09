package com.ocdsoft.bacta.swg.precu.object.intangible.schematic;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.precu.object.intangible.IntangibleObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerDraftSchematicObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 */
public class DraftSchematicObject extends IntangibleObject {
    @Inject
    public DraftSchematicObject(final ObjectTemplateList objectTemplateList,
                                final SlotIdManager slotIdManager,
                                final ServerDraftSchematicObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
