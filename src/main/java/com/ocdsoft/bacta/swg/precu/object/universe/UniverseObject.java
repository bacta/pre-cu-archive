package com.ocdsoft.bacta.swg.precu.object.universe;


import com.ocdsoft.bacta.swg.precu.object.ServerObject;
import com.ocdsoft.bacta.swg.precu.object.template.server.ServerUniverseObjectTemplate;
import com.ocdsoft.bacta.swg.precu.object.template.shared.SharedObjectTemplate;

/**
 * Created by crush on 9/3/2014.
 *
 * A UniverseObject is an object that is global to the entire server
 * cluster.  UniverseObjects represent global data (such as Resource
 * Classes) or objects with no definite location (such as Resource Pools).
 */
public abstract class UniverseObject extends ServerObject {
    private static SharedObjectTemplate defaultSharedObjectTemplate; //gets set by a service at runtime.

    public UniverseObject(final ServerUniverseObjectTemplate template) {
        super(template, false);
    }
}
