package com.ocdsoft.bacta.swg.precu.zone;

import com.ocdsoft.bacta.swg.precu.object.tangible.TangibleObject;

import java.util.Map;

public interface ZoneMap extends Map<String, Zone> {

    void addObject(Zone zone, TangibleObject obj);
    void removeObject(Zone zone, TangibleObject obj);

}
