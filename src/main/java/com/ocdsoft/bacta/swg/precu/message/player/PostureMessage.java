package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;

/**
 * Created by Kyle on 3/24/14.
 */
public class PostureMessage extends ObjControllerMessage {

    public PostureMessage(SceneObject scno, byte postureId) {
        super(0x1B, 0x131, scno.getNetworkId(), 0);

        writeByte(postureId);
        writeByte(0);
    }

}
