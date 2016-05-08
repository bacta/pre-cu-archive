package com.ocdsoft.bacta.swg.precu.object.intangible.manufacture;

import com.ocdsoft.bacta.swg.precu.object.intangible.IntangibleObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerManufactureSchematicObjectTemplate;

/**
 * Created by crush on 9/4/2014.
 */
public class ManufactureSchematicObject extends IntangibleObject {
    //private final AutoDeltaInt itemsPerContainer;
    //private final AutoDeltaMap<StringId, Float> attributes;
    //private final AutoDeltaFloat manufactureTime;
    //private final AutoDeltaInt draftSchematicSharedTemplate;
    //private final AutoDeltaString customAppearance;
    //private final AutoDeltaString appearanceData;
    //private final AutoDeltaBoolean isCrafting;
    //private final AutoDeltaByte schematicChangedSignal;

    public ManufactureSchematicObject(final ServerManufactureSchematicObjectTemplate template) {
        super(template);
    }
}
