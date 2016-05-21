package com.ocdsoft.bacta.swg.server.zone;

import com.ocdsoft.bacta.swg.server.object.tangible.TangibleObject;

import java.util.Map;

public interface ZoneMap extends Map<String, Zone> {

    void addObject(Zone zone, TangibleObject obj);
    void removeObject(Zone zone, TangibleObject obj);

}
