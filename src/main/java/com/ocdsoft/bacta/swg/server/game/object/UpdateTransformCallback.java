package com.ocdsoft.bacta.swg.server.game.object;

import com.google.common.collect.ImmutableSet;
import com.ocdsoft.bacta.swg.server.game.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.server.game.util.DistanceUtil;
import org.magnos.steer.spatial.SearchCallback;
import org.magnos.steer.spatial.SpatialEntity;
import org.magnos.steer.vec.Vec;

import java.util.Set;
import java.util.TreeSet;

public class UpdateTransformCallback implements SearchCallback {

	private TangibleObject caller;

	private Set<TangibleObject> nearObjects = new TreeSet<>();
	
	public UpdateTransformCallback(TangibleObject caller) {
		this.caller = caller;
	}

    @Override
    public boolean onFound(SpatialEntity entity,
                           float overlap,
                           int index,
                           Vec queryOffset,
                           float queryRadius,
                           int queryMax,
                           long queryGroups) {


        TangibleObject tano = (TangibleObject) entity;

        int distance = DistanceUtil.approxDistance(
                (int)(caller.getPosition().x - tano.getPosition().x),
                (int)(caller.getPosition().y - tano.getPosition().y));

        if(distance <= queryRadius) {
            nearObjects.add(tano);
            return true;
        }

        return false;
    }

    public ImmutableSet<TangibleObject> getNearObjects() {
        return ImmutableSet.copyOf(nearObjects);
    }


}
