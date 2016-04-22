package com.ocdsoft.bacta.swg.precu.message.object.command;

import com.ocdsoft.bacta.engine.buffer.ByteBufferSerializable;
import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.swg.precu.message.object.ObjControllerMessage;
import lombok.Data;
import lombok.Getter;

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
