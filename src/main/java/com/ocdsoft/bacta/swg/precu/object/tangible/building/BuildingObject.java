package com.ocdsoft.bacta.swg.precu.object.tangible.building;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerBuildingObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public final class BuildingObject extends TangibleObject {
    @Inject
    public BuildingObject(final ObjectTemplateList objectTemplateList,
                          final SlotIdManager slotIdManager,
                          final ServerBuildingObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
