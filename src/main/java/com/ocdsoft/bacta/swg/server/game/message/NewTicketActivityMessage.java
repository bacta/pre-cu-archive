package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      00 00 00 00 

  SOECRC32.hashCode(NewTicketActivityMessage.class.getSimpleName()); // 0x274f4e78
  */
@Getter
@Priority(0x2)
public final class NewTicketActivityMessage extends GameNetworkMessage {

    public NewTicketActivityMessage() {

    }

    public NewTicketActivityMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
