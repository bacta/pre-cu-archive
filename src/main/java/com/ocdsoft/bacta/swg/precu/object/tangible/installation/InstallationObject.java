package com.ocdsoft.bacta.swg.precu.object.tangible.installation;

import com.ocdsoft.bacta.swg.precu.object.archive.delta.*;
import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

public class InstallationObject extends TangibleObject {
    @Override
    public int getObjectType() {
        return 0x494E534F;
    } //'INSO'

    //InstallationObjectMessage03
    private final AutoDeltaBoolean activated = new AutoDeltaBoolean(false, sharedPackage);
    private final AutoDeltaFloat power = new AutoDeltaFloat(0.0F, sharedPackage);
    private final AutoDeltaFloat powerRate = new AutoDeltaFloat(0.0F, sharedPackage);

    //InstallationObjectMessage07
    private final AutoDeltaBoolean resourcePoolUpdateFlag = new AutoDeltaBoolean(false, uiPackage);
    private final AutoDeltaLong resourceIds = new AutoDeltaLong(0L, uiPackage);
    private final AutoDeltaLong resourceIdsSecondary = new AutoDeltaLong(0L, uiPackage);
    private final AutoDeltaVector<String> resourceNames = new AutoDeltaVector<String>(uiPackage);
    private final AutoDeltaVector<String> resourceTypes = new AutoDeltaVector<String>(uiPackage);
    private final AutoDeltaLong selectedResourceId = new AutoDeltaLong(0L, uiPackage);
    private final AutoDeltaBoolean operating = new AutoDeltaBoolean(false, uiPackage);
    private final AutoDeltaInt extractionRateDisplayed = new AutoDeltaInt(0, uiPackage);
    private final AutoDeltaFloat extractionRateMax = new AutoDeltaFloat(0.0F, uiPackage);
    private final AutoDeltaFloat extractionRate = new AutoDeltaFloat(0.0F, uiPackage);
    private final AutoDeltaFloat hopperSize = new AutoDeltaFloat(0.0F, uiPackage);
    private final AutoDeltaInt hopperSizeMax = new AutoDeltaInt(0, uiPackage);
    private final AutoDeltaBoolean updateHopperContents = new AutoDeltaBoolean(false, uiPackage);
    private final AutoDeltaVector<HopperEntry> hopperList = new AutoDeltaVector<HopperEntry>(uiPackage);
    private final AutoDeltaByte conditionPercentage = new AutoDeltaByte(100, uiPackage); //TODO This seems fishy.
}
