package com.ocdsoft.bacta.swg.precu.object.universe.guild;

import com.ocdsoft.bacta.swg.precu.object.archive.delta.set.AutoDeltaStringSet;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerGuildObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.universe.UniverseObject;

public final class GuildObject extends UniverseObject {

    private final AutoDeltaStringSet abbrevs;

    public GuildObject(final ServerGuildObjectTemplate template) {
        super(template);

        abbrevs = new AutoDeltaStringSet();

        sharedPackage.addVariable(abbrevs);
    }
}
