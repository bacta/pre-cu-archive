package com.ocdsoft.bacta.swg.server.game.controller.object;

import com.ocdsoft.bacta.swg.server.game.message.object.GameControllerMessageType;
import com.ocdsoft.bacta.swg.server.game.message.object.MessageQueueData;
import com.ocdsoft.bacta.swg.server.game.message.object.ObjControllerMessage;

/**
 * Created by crush on 5/29/2016.
 */
public class ObjControllerBuilder {
    private int flags;
    private float value;

    private ObjControllerBuilder() {
    }

    public static ObjControllerBuilder newBuilder() {
        return new ObjControllerBuilder();
    }

    public ObjControllerBuilder value(float value) {
        this.value = value;
        return this;
    }

    public ObjControllerBuilder send() {
        flags |= GameControllerMessageFlags.SEND;
        return this;
    }

    public ObjControllerBuilder reliable() {
        flags |= GameControllerMessageFlags.RELIABLE;
        return this;
    }

    public ObjControllerBuilder authClient() {
        flags |= GameControllerMessageFlags.DEST_AUTH_CLIENT;
        return this;
    }

    public ObjControllerBuilder proxyClient() {
        flags |= GameControllerMessageFlags.DEST_PROXY_CLIENT;
        return this;
    }

    public ObjControllerMessage build(final long networkId, final GameControllerMessageType type, final MessageQueueData data) {
        return new ObjControllerMessage(flags, type.value, networkId, value, data);
    }
}
