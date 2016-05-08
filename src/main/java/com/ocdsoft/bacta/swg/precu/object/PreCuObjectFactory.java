package com.ocdsoft.bacta.swg.precu.object;

import com.ocdsoft.bacta.swg.precu.object.template.server.ServerObjectTemplate;
import com.ocdsoft.bacta.swg.shared.object.ObjectFactory;

/**
 * Created by crush on 5/6/2016.
 */
public interface PreCuObjectFactory<T extends ServerObject, U extends ServerObjectTemplate>
        extends ObjectFactory<T, U> {
}
