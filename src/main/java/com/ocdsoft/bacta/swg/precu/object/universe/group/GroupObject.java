package com.ocdsoft.bacta.swg.precu.object.universe.group;

import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaLong;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaShort;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.AutoDeltaString;
import com.ocdsoft.bacta.swg.precu.object.archive.delta.vector.AutoDeltaObjectVector;
import com.ocdsoft.bacta.swg.precu.object.tangible.ship.ShipFormationGroupMember;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerGroupObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.universe.UniverseObject;

public final class GroupObject extends UniverseObject {
    public static class LootRule {
        public static final int FREE_FOR_ALL = 0x00;
        public static final int MASTER_LOOTER = 0x01;
        public static final int LOTTERY = 0x02;
        public static final int RANDOM = 0x03;
    }

    private final AutoDeltaObjectVector<GroupMember> groupMembers;
    private final AutoDeltaObjectVector<ShipFormationGroupMember> groupShipFormationMembers;
    private final AutoDeltaString groupName;
    private final AutoDeltaShort groupLevel;
    private final AutoDeltaInt formationNameCrc;
    private final AutoDeltaLong lootMaster;
    private final AutoDeltaInt lootRule;

    public GroupObject(final ServerGroupObjectTemplate template) {
        super(template);

        groupMembers = new AutoDeltaObjectVector<>(GroupMember::new);
        groupShipFormationMembers = new AutoDeltaObjectVector<>(ShipFormationGroupMember::new);
        groupName = new AutoDeltaString("");
        groupLevel = new AutoDeltaShort();
        formationNameCrc = new AutoDeltaInt();
        lootMaster = new AutoDeltaLong();
        lootRule = new AutoDeltaInt(LootRule.FREE_FOR_ALL);

        sharedPackageNp.addVariable(groupMembers);
        sharedPackageNp.addVariable(groupShipFormationMembers);
        sharedPackageNp.addVariable(groupName);
        sharedPackageNp.addVariable(groupLevel);
        sharedPackageNp.addVariable(formationNameCrc);
        sharedPackageNp.addVariable(lootMaster);
        sharedPackageNp.addVariable(lootRule);
    }
}
