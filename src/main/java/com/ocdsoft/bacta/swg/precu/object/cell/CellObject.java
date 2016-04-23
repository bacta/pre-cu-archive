package com.ocdsoft.bacta.swg.precu.object.cell;

import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaVariable;
import com.ocdsoft.bacta.swg.shared.math.Vector;

/**
 * Created by crush on 8/18/2014.
 */
public class CellObject extends ServerObject {
    private final AutoDeltaBoolean isPublic = new AutoDeltaBoolean(true, sharedPackage);
    private final AutoDeltaInt cellNumber = new AutoDeltaInt(0, sharedPackage);
    private final AutoDeltaVariable<UnicodeString> cellLabel = new AutoDeltaVariable<>(UnicodeString.EMPTY, sharedPackageNp);
    private final AutoDeltaVariable<Vector> labelLocationOffset = new AutoDeltaVariable<>(new Vector(), sharedPackageNp);
}
