package com.ocdsoft.bacta.swg.precu.object.cell;

import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaVariable;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerCellObjectTemplate;
import com.ocdsoft.bacta.swg.shared.math.Vector;

/**
 * Created by crush on 8/18/2014.
 */
public class CellObject extends ServerObject {
    private final AutoDeltaBoolean isPublic;
    private final AutoDeltaInt cellNumber;
    private final AutoDeltaVariable<UnicodeString> cellLabel;
    private final AutoDeltaVariable<Vector> labelLocationOffset;

    public CellObject(final ServerCellObjectTemplate template) {
        super(template, false);

        isPublic = new AutoDeltaBoolean(true);
        cellNumber = new AutoDeltaInt(0);
        cellLabel = new AutoDeltaVariable<>(UnicodeString.EMPTY, UnicodeString::new);
        labelLocationOffset = new AutoDeltaVariable<>(new Vector(Vector.ZERO), Vector::new);

        sharedPackage.addVariable(isPublic);
        sharedPackage.addVariable(cellNumber);
        sharedPackageNp.addVariable(cellLabel);
        sharedPackageNp.addVariable(labelLocationOffset);
    }
}
