package com.ocdsoft.bacta.swg.precu.object.tangible.factory;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerFactoryObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public final class FactoryObject extends TangibleObject {
    @Inject
    public FactoryObject(final ObjectTemplateList objectTemplateList,
                         final SlotIdManager slotIdManager,
                         final ServerFactoryObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
