package com.ocdsoft.bacta.swg.server.game.object.cell;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaVariable;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

/**
 * Created by crush on 8/18/2014.
 */
public class CellObject extends ServerObject {
    private final AutoDeltaBoolean isPublic;
    private final AutoDeltaInt cellNumber;
    private final AutoDeltaVariable<UnicodeString> cellLabel;
    private final AutoDeltaVariable<Vector> labelLocationOffset;

    @Inject
    public CellObject(final ObjectTemplateList objectTemplateList,
                      final SlotIdManager slotIdManager,
                      final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);

        isPublic = new AutoDeltaBoolean(true);
        cellNumber = new AutoDeltaInt(0);
        cellLabel = new AutoDeltaVariable<>(UnicodeString.EMPTY, UnicodeString::new);
        labelLocationOffset = new AutoDeltaVariable<>(new Vector(Vector.ZERO), Vector::new);
    }

    private void addMembersToPackages() {
        sharedPackage.addVariable(isPublic);
        sharedPackage.addVariable(cellNumber);
        sharedPackageNp.addVariable(cellLabel);
        sharedPackageNp.addVariable(labelLocationOffset);
    }
}
