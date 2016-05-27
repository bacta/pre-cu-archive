package com.ocdsoft.bacta.swg.server.game.object.tangible.installation.manufacture;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.server.game.object.tangible.installation.InstallationObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerManufactureInstallationObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 5/8/2016.
 */
public class ManufactureInstallationObject extends InstallationObject {
    @Inject
    public ManufactureInstallationObject(final ObjectTemplateList objectTemplateList,
                                         final SlotIdManager slotIdManager,
                                         final ServerManufactureInstallationObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);
    }
}
