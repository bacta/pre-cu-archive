package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Has no body. Gets sent from the client->server, then the server turns around and sends the same message
 * back to the client.
 */
@Getter
@Priority(0x1)
public final class CmdSceneReady extends GameNetworkMessage {

    public CmdSceneReady() {

    }

    public CmdSceneReady(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
