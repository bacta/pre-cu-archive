package com.ocdsoft.bacta.swg.precu.object.intangible;

import com.ocdsoft.bacta.swg.precu.object.PreCuObjectFactory;
import com.ocdsoft.bacta.swg.precu.object.ServerObjectFactory;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerIntangibleObjectTemplate;

/**
 * Created by crush on 5/7/2016.
 */
public class IntangibleObjectFactory implements PreCuObjectFactory<IntangibleObject, ServerIntangibleObjectTemplate> {
    private final ServerObjectFactory serverObjectFactory;

    public IntangibleObjectFactory(final ServerObjectFactory serverObjectFactory) {
        this.serverObjectFactory = serverObjectFactory;
    }

    @Override
    public IntangibleObject create(ServerIntangibleObjectTemplate template) {
        final IntangibleObject intangibleObject = new IntangibleObject(template);
        initialize(intangibleObject, template);
        return intangibleObject;
    }

    @Override
    public void initialize(IntangibleObject object, ServerIntangibleObjectTemplate template) {
        serverObjectFactory.initialize(object, template);
    }
}
