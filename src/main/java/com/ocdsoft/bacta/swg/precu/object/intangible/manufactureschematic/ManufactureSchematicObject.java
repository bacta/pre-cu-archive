package com.ocdsoft.bacta.swg.precu.object.intangible.manufactureschematic;

import com.ocdsoft.bacta.swg.precu.object.intangible.IntangibleObject;

/**
 * Created by crush on 9/4/2014.
 */
public class ManufactureSchematicObject extends IntangibleObject {
    @Override
    public int getObjectType() {
        return 0x4D53434F;
    } //'MSCO'

    //private final AutoDeltaInt itemsPerContainer;
    //private final AutoDeltaMap<StringId, Float> attributes;
    //private final AutoDeltaFloat manufactureTime;
    //private final AutoDeltaInt draftSchematicSharedTemplate;
    //private final AutoDeltaString customAppearance;
    //private final AutoDeltaString appearanceData;
    //private final AutoDeltaBoolean isCrafting;
    //private final AutoDeltaByte schematicChangedSignal;
}
