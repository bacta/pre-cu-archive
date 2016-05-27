package com.ocdsoft.bacta.swg.server.game.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.dispatch.MessageCRC;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
      00 00 00 00 

  */
@Getter
@Priority(0x2)
@MessageCRC(0x173b91c2)
@AllArgsConstructor
public final class ClientInactivityMessage extends GameNetworkMessage {

    private final boolean inactive;

    public ClientInactivityMessage(final ByteBuffer buffer) {
        inactive = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, inactive);
    }
}
