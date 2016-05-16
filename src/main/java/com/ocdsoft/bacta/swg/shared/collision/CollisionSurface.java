package com.ocdsoft.bacta.swg.shared.collision;

import com.ocdsoft.bacta.swg.shared.collision.extent.BaseExtent;
import com.ocdsoft.bacta.swg.shared.math.Transform;

/**
 * Created by crush on 5/10/2016.
 */
public interface CollisionSurface {
    Transform getTransformObjectToParent();

    Transform getTransformObjectToWorld();

    float getScale();

    BaseExtent getExtentInLocal();

    BaseExtent getExtentInParent();
}
