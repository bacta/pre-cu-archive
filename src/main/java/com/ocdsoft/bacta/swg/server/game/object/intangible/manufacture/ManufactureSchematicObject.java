package com.ocdsoft.bacta.swg.server.game.object.intangible.manufacture;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.archive.delta.*;
import com.ocdsoft.bacta.swg.archive.delta.map.AutoDeltaObjectFloatMap;
import com.ocdsoft.bacta.swg.server.game.object.intangible.IntangibleObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 9/4/2014.
 */
public class ManufactureSchematicObject extends IntangibleObject {
    private final AutoDeltaInt itemsPerContainer;
    private final AutoDeltaObjectFloatMap<StringId> attributes;
    private final AutoDeltaFloat manufactureTime;
    private final AutoDeltaInt draftSchematicSharedTemplate;
    private final AutoDeltaString customAppearance;
    private final AutoDeltaString appearanceData;
    private final AutoDeltaBoolean isCrafting;
    private final AutoDeltaByte schematicChangedSignal;

    @Inject
    public ManufactureSchematicObject(final ObjectTemplateList objectTemplateList,
                                      final SlotIdManager slotIdManager,
                                      final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);

        attributes = new AutoDeltaObjectFloatMap<>(StringId::new);
        itemsPerContainer = new AutoDeltaInt();
        manufactureTime = new AutoDeltaFloat();
        appearanceData = new AutoDeltaString();
        customAppearance = new AutoDeltaString();
        draftSchematicSharedTemplate = new AutoDeltaInt();
        isCrafting = new AutoDeltaBoolean();
        schematicChangedSignal = new AutoDeltaByte();

        addMembersToPackages();
    }

    private void addMembersToPackages() {
        sharedPackage.addVariable(attributes);
        sharedPackage.addVariable(itemsPerContainer);
        sharedPackage.addVariable(manufactureTime);
        sharedPackageNp.addVariable(appearanceData);
        sharedPackageNp.addVariable(customAppearance);
        sharedPackageNp.addVariable(draftSchematicSharedTemplate);
        sharedPackageNp.addVariable(isCrafting);
        sharedPackageNp.addVariable(schematicChangedSignal);
    }
}
