package com.ocdsoft.bacta.swg.server.object.tangible.resource;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.object.template.server.ServerResourceContainerObjectTemplate;
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
                                   final ServerResourceContainerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
