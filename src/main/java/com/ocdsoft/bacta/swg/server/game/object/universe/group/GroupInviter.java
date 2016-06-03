package com.ocdsoft.bacta.swg.server.game.object.universe.group;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/12/2014.
 */
@Getter
@AllArgsConstructor
public class GroupInviter implements ByteBufferWritable {
    private final long inviterId;
    private final String name;
    private final long inviterShipId;

    public GroupInviter() {
        inviterId = 0;
        name = "";
        inviterShipId = 0;
    }

    public GroupInviter(final ByteBuffer buffer) {
        inviterId = buffer.getLong();
        //ship and name aren't in the buffer apparently!?
        this.name = "";
        this.inviterShipId = 0;
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(inviterId);
        //ship and name aren't in the buffer apparently!?
    }
}
