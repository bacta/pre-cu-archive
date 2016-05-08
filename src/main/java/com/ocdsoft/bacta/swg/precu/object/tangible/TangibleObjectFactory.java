package com.ocdsoft.bacta.swg.precu.object.tangible;

import com.ocdsoft.bacta.swg.precu.object.PreCuObjectFactory;
import com.ocdsoft.bacta.swg.precu.object.ServerObjectFactory;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerTangibleObjectTemplate;

/**
 * Created by crush on 5/6/2016.
 */
public class TangibleObjectFactory implements PreCuObjectFactory<TangibleObject, ServerTangibleObjectTemplate> {
    private final ServerObjectFactory serverObjectFactory;

    public TangibleObjectFactory(final ServerObjectFactory serverObjectFactory) {
        this.serverObjectFactory = serverObjectFactory;
    }

    @Override
    public TangibleObject create(final ServerTangibleObjectTemplate template) {
        final TangibleObject tangibleObject = new TangibleObject(template);
        initialize(tangibleObject, template);
        return tangibleObject;
    }

    @Override
    public void initialize(final TangibleObject object, final ServerTangibleObjectTemplate template) {
        serverObjectFactory.initialize(object, template);

        //TangibleObject initialization.
    }
}
