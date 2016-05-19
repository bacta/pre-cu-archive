package com.ocdsoft.bacta.swg.server.object.tangible.building;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerBuildingObjectTemplate;
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
