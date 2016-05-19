package com.ocdsoft.bacta.swg.server.object.intangible;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.server.object.ServerObject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerIntangibleObjectTemplate;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public class IntangibleObject extends ServerObject {
    private final AutoDeltaInt count;

    @Inject
    public IntangibleObject(final ObjectTemplateList objectTemplateList,
                            final SlotIdManager slotIdManager,
                            final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);

        assert template instanceof ServerIntangibleObjectTemplate;

        final ServerIntangibleObjectTemplate objectTemplate = (ServerIntangibleObjectTemplate) template;

        count = new AutoDeltaInt(objectTemplate.getCount());

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
