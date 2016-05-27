package com.ocdsoft.bacta.swg.server.game.object.universe.guild;

import com.google.inject.Inject;
import com.ocdsoft.bacta.swg.archive.delta.set.AutoDeltaStringSet;
import com.ocdsoft.bacta.swg.server.game.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.server.game.object.universe.UniverseObject;
import com.ocdsoft.bacta.swg.shared.container.SlotIdManager;
import com.ocdsoft.bacta.swg.shared.template.ObjectTemplateList;

public final class GuildObject extends UniverseObject {

    private final AutoDeltaStringSet abbrevs;

    @Inject
    public GuildObject(final ObjectTemplateList objectTemplateList,
                       final SlotIdManager slotIdManager,
                       final ServerObjectTemplate template) {
        super(objectTemplateList, slotIdManager, template);

        abbrevs = new AutoDeltaStringSet();

        sharedPackage.addVariable(abbrevs);
    }
}
