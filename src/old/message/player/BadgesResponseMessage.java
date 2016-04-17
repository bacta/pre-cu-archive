package com.ocdsoft.bacta.swg.precu.message.player;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.precu.object.SceneObject;

/**
 * Created by crush on 8/15/2014.
 */
public class BadgesResponseMessage extends GameNetworkMessage {
    public BadgesResponseMessage(SceneObject requester, SceneObject target) {
        super(0x03, 0x6D89D25B);

        writeLong(requester.getNetworkId());
        writeInt(0x05);

        for (int i = 0; i < 5; i++) {
            writeInt(0);
        }
    }
}
