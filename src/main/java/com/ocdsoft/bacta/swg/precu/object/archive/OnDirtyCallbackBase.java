package com.ocdsoft.bacta.swg.precu.object.archive;

import com.ocdsoft.bacta.swg.precu.object.ServerObject;

/**
 * Created by crush on 8/16/2014.
 */
public interface OnDirtyCallbackBase {
    void onDirty(ServerObject sceneObject);
}
