package com.ocdsoft.bacta.swg.precu.object.intangible.waypoint;

import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.shared.math.Vector;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaBoolean;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaLong;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaString;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaVariable;
import com.ocdsoft.bacta.swg.precu.object.intangible.IntangibleObject;


public final class WaypointObject extends IntangibleObject {
    @Override
    public int getOpcode() {
        return 0x57415950;
    } //'WAYP'

    //WaypointObjectMessage03
    private final AutoDeltaVariable<Vector> location = new AutoDeltaVariable<>(new Vector(0, 0, 0), sharedPackage);
    private final AutoDeltaBoolean waypointActive = new AutoDeltaBoolean(false, sharedPackage);
    private final AutoDeltaLong cell = new AutoDeltaLong(0L, sharedPackage);
    private final AutoDeltaString planetName = new AutoDeltaString("", sharedPackage);
    private final AutoDeltaVariable<UnicodeString> waypointName = new AutoDeltaVariable<>(UnicodeString.EMPTY, sharedPackage);
    private final AutoDeltaBoolean waypointVisible = new AutoDeltaBoolean(true, sharedPackage);
    private final AutoDeltaString waypointColor = new AutoDeltaString("blue", sharedPackage);
}
