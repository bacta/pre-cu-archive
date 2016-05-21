package com.ocdsoft.bacta.swg.precu.message.game.object;

/**
 * Created by crush on 8/14/2014.
 */
public class BiographyRetrieved extends ObjControllerMessage {
    public BiographyRetrieved(long requesterId, long targetId, String biography) {
        super(0x1B, 0x1DB, requesterId, 0);

        writeLong(targetId);
        writeUnicode(biography);
    }
}
