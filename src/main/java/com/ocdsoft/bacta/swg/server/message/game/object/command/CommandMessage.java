package com.ocdsoft.bacta.swg.server.message.game.object.command;

import com.ocdsoft.bacta.swg.server.message.game.object.ObjControllerMessage;
import lombok.Data;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/17/2016.
 */
@Data
public abstract class CommandMessage extends ObjControllerMessage {

    private int sequenceId;
    private int commandHash;
    private long targetId;
    private String params;  // Unicode

    public CommandMessage(final ByteBuffer buffer) {
        super(buffer);
    }

}
