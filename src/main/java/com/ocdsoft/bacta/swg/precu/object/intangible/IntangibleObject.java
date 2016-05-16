package com.ocdsoft.bacta.swg.precu.object.intangible;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerIntangibleObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public class IntangibleObject extends ServerObject {
    private final AutoDeltaInt count;

    @Inject
    public IntangibleObject(final ObjectTemplateList objectTemplateList,
                            final SlotIdManager slotIdManager,
                            final ServerIntangibleObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);

        count = new AutoDeltaInt(template.getCount());

        addMembersToPackages();
    }

    private void addMembersToPackages() {
        sharedPackage.addVariable(count);
    }

    public enum TheaterLocationType {
        NONE,
        GET_GOOD_LOCATION,
        FLATTEN
    }

    ;
}
