package com.ocdsoft.bacta.swg.server.object.tangible.vehicle;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerVehicleObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 */
public class VehicleObject extends TangibleObject {
    @Inject
    public VehicleObject(final ObjectTemplateList objectTemplateList,
                         final SlotIdManager slotIdManager,
                         final ServerVehicleObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
