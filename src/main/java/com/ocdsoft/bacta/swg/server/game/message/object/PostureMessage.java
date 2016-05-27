package com.ocdsoft.bacta.swg.server.game.message.object;

import com.ocdsoft.bacta.swg.server.game.object.ServerObject;

import java.nio.ByteBuffer;

/**
 * Created by Kyle on 3/24/14.
 */
public final class PostureMessage extends ObjControllerMessage {

    private byte postureId;
    private byte unknown;

    private PostureMessage(long objectId) {
        super(0x1B, 0x131, objectId, 0);
    }

    public PostureMessage(ServerObject scno, byte postureId) {
        this(scno.getNetworkId());

        this.postureId = postureId;
        this.unknown = 0;
    }

    public PostureMessage(final ByteBuffer buffer) {
        this(0);

        this.postureId = buffer.get();
        this.unknown = buffer.get();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.put(postureId);
        buffer.put((byte) 0);
    }
}
