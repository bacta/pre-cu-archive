package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      02 00 65 6E 

  SOECRC32.hashCode(RequestCategoriesMessage.class.getSimpleName()); // 0xf898e25f
  */
@Getter
@Priority(0x2)
public final class RequestCategoriesMessage extends GameNetworkMessage {

    public RequestCategoriesMessage() {

    }

    public RequestCategoriesMessage(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
