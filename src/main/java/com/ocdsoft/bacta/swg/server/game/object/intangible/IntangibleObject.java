package com.ocdsoft.bacta.swg.server.game.object.intangible;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.archive.delta.AutoDeltaInt;
import com.ocdsoft.bacta.swg.server.game.object.ServerObject;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerIntangibleObjectTemplate;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public class IntangibleObject extends ServerObject {
    private final AutoDeltaInt count;

    @Inject
    public IntangibleObject(final ObjectTemplateList objectTemplateList,
                            final SlotIdManager slotIdManager,
                            final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template, false);

        assert template instanceof ServerIntangibleObjectTemplate;

        final ServerIntangibleObjectTemplate objectTemplate = (ServerIntangibleObjectTemplate) template;

        count = new AutoDeltaInt(objectTemplate.getCount());

        addMembersToPackages();
    }

    private void addMembersToPackages() {
        sharedPackage.addVariable(count);
    }

    @Override
    protected void sendObjectSpecificBaselinesToClient(final SoeUdpConnection client) {
        //IsFlattenedTheaterMessage<pair<long, bool>>
    }

    public enum TheaterLocationType {
        NONE,
        GET_GOOD_LOCATION,
        FLATTEN
    }

    ;
}
