package com.ocdsoft.bacta.swg.precu.message.game.object;

/**
 * Created by Kyle on 3/24/14.
 */
public class CommandQueueRemove extends ObjControllerMessage {

    public CommandQueueRemove(long objId, int actionCounter, float timer, int error, int action) {
        super(0x1B, 0x117, objId, 0);

        //int sequenceId
        //float waitTime
        //int status
        //int statusDetail

        writeInt(actionCounter);
        writeFloat(timer);
        writeInt(error);
        writeInt(action);
    }

}
