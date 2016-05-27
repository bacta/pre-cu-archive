package com.ocdsoft.bacta.swg.server.game.object.tangible.resource;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 9/4/2014.
 */
public class ResourceContainerObject extends TangibleObject {
    //private final AutoDeltaLong resourceType;
    //private final AutoDeltaVariable<StringId> resourceNameId;
    //private final AutoDeltaInt quantity;
    //private final AutoDeltaInt maxQuantity;
    //private final AutoDeltaVariable<UnicodeString> resourceName;
    //private final AutoDeltaString parentName;

    @Inject
    public ResourceContainerObject(final ObjectTemplateList objectTemplateList,
                                   final SlotIdManager slotIdManager,
                                   final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
