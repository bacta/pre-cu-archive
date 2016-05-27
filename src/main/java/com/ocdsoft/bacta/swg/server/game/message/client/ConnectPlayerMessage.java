package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      00 00 00 00 

  SOECRC32.hashCode(ConnectPlayerMessage.class.getSimpleName()); // 0x2e365218
  */
@Getter
@Priority(0x2)
public final class ConnectPlayerMessage extends GameNetworkMessage {

    public ConnectPlayerMessage() {

    }

    public ConnectPlayerMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
