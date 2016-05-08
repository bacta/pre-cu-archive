package com.ocdsoft.bacta.swg.precu.object.intangible;

import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerIntangibleObjectTemplate;

public class IntangibleObject extends ServerObject {
    private final AutoDeltaInt count;

    public IntangibleObject(final ServerIntangibleObjectTemplate template) {
        super(template, false);

        count = new AutoDeltaInt(template.getCount());

        sharedPackage.addVariable(count);
    }

    public enum TheaterLocationType {
        NONE,
        GET_GOOD_LOCATION,
        FLATTEN
    }

    ;
}
