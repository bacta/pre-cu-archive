package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.precu.object.template.server.ServerStaticObjectTemplate;

/**
 * Created by crush on 5/8/2016.
 */
public class StaticObject extends ServerObject {
    public StaticObject(final ServerStaticObjectTemplate template) {
        super(template, false);
    }
}
