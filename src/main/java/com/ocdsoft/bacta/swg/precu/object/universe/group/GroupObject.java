package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.swg.precu.object.archive.delta.*;
import com.ocdsoft.bacta.swg.precu.object.universe.UniverseObject;

public final class GroupObject extends UniverseObject {
    @Override
    public int getOpcode() {
        return 0x47525550;
    } //'GRUP'

    public static class LootRule {
        public static final int FreeForAll = 0x00;
        public static final int MasterLooter = 0x01;
        public static final int Lottery = 0x02;
        public static final int Random = 0x03;
    }

    //GroupObjectMessage06
    private final AutoDeltaVector<GroupMember> groupMembers = new AutoDeltaVector<>(sharedPackageNp); //pair<long, string>
    private final AutoDeltaVector<ShipFormationGroupMember> groupShipFormationMembers = new AutoDeltaVector<>(sharedPackageNp); //pair<long, int>
    private final AutoDeltaString groupName = new AutoDeltaString("", sharedPackageNp);
    private final AutoDeltaShort groupLevel = new AutoDeltaShort(0, sharedPackageNp);
    private final AutoDeltaInt formationNameCrc = new AutoDeltaInt(0, sharedPackageNp);
    private final AutoDeltaLong lootMaster = new AutoDeltaLong(0L, sharedPackageNp);
    private final AutoDeltaInt lootRule = new AutoDeltaInt(LootRule.FreeForAll, sharedPackageNp);
}
