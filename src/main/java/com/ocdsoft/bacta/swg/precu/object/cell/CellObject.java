package com.ocdsoft.bacta.swg.precu.object.cell;

import com.ocdsoft.bacta.swg.network.soe.lang.UnicodeString;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;
import com.ocdsoft.bacta.swg.precu.object.Vector;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaVariable;

/**
 * Created by crush on 8/18/2014.
 */
public class CellObject extends SceneObject {
    private final AutoDeltaBoolean isPublic = new AutoDeltaBoolean(true, sharedPackage);
    private final AutoDeltaInt cellNumber = new AutoDeltaInt(0, sharedPackage);
    private final AutoDeltaVariable<UnicodeString> cellLabel = new AutoDeltaVariable<>(UnicodeString.empty, sharedPackageNp);
    private final AutoDeltaVariable<Vector> labelLocationOffset = new AutoDeltaVariable<>(Vector.zero, sharedPackageNp);
}
