package com.ocdsoft.bacta.swg.precu.message.game.object.command;

import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.precu.message.game.object.ObjControllerMessage;
import lombok.Data;

/**
 * Created by kyle on 4/17/2016.
 */
@Data
public abstract class CommandMessage extends ObjControllerMessage {

    private int sequenceId;
    private int commandHash;
    private long targetId;
    private UnicodeString params;

}
