package com.ocdsoft.bacta.swg.server.game.object;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.template.shared.SharedObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.ArrangementDescriptorList;
import com.ocdsoft.bacta.swg.shared.container.SlotDescriptorList;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplate;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;
import com.ocdsoft.bacta.tre.TreeFile;

/**
 * Created by crush on 5/10/2016.
 */
@Singleton
public class PreCuObjectTemplateList extends ObjectTemplateList {
    private final ArrangementDescriptorList arrangementDescriptorList;
    private final SlotDescriptorList slotDescriptorList;

    @Inject
    public PreCuObjectTemplateList(final TreeFile treeFile,
                                   final ArrangementDescriptorList arrangementDescriptorList,
                                   final SlotDescriptorList slotDescriptorList) {
        super(treeFile);
        this.arrangementDescriptorList = arrangementDescriptorList;
        this.slotDescriptorList = slotDescriptorList;
    }

    @Override
    protected void postFetch(final ObjectTemplate objectTemplate) {
        if (objectTemplate instanceof SharedObjectTemplate) {
            final SharedObjectTemplate sharedObjectTemplate = (SharedObjectTemplate) objectTemplate;

            final String slotFilename = sharedObjectTemplate.getSlotDescriptorFilename();
            final String arrangementFilename = sharedObjectTemplate.getArrangementDescriptorFilename();

            if (slotFilename != null && !slotFilename.isEmpty())
                sharedObjectTemplate.setSlotDescriptor(slotDescriptorList.fetch(slotFilename));

            if (arrangementFilename != null && !arrangementFilename.isEmpty())
                sharedObjectTemplate.setArrangementDescriptor(arrangementDescriptorList.fetch(arrangementFilename));
        }
    }
}
