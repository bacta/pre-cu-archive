package com.ocdsoft.bacta.swg.server.game.object.universe.group;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaLong;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaShort;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaString;
import com.ocdsoft.bacta.swg.archive.delta.vector.AutoDeltaObjectVector;
import com.ocdsoft.bacta.swg.server.game.object.tangible.ship.ShipFormationGroupMember;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.server.game.object.universe.UniverseObject;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

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

    @Inject
    public GroupObject(final ObjectTemplateList objectTemplateList,
                       final SlotIdManager slotIdManager,
                       final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);

        groupMembers = new AutoDeltaObjectVector<>(GroupMember::new);
        groupShipFormationMembers = new AutoDeltaObjectVector<>(ShipFormationGroupMember::new);
        groupName = new AutoDeltaString("");
        groupLevel = new AutoDeltaShort();
        formationNameCrc = new AutoDeltaInt();
        lootMaster = new AutoDeltaLong();
        lootRule = new AutoDeltaInt(LootRule.FREE_FOR_ALL);

        addMembersToPackages();
    }

    private void addMembersToPackages() {
        sharedPackageNp.addVariable(groupMembers);
        sharedPackageNp.addVariable(groupShipFormationMembers);
        sharedPackageNp.addVariable(groupName);
        sharedPackageNp.addVariable(groupLevel);
        sharedPackageNp.addVariable(formationNameCrc);
        sharedPackageNp.addVariable(lootMaster);
        sharedPackageNp.addVariable(lootRule);
    }
}
