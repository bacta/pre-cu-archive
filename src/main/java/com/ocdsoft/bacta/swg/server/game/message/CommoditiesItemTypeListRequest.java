package com.ocdsoft.bacta.swg.server.game.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      00 00 

  SOECRC32.hashCode(CommoditiesItemTypeListRequest.class.getSimpleName()); // 0x48f493c5
  */
@Getter
@Priority(0x2)
public final class CommoditiesItemTypeListRequest extends GameNetworkMessage {

    public CommoditiesItemTypeListRequest() {

    }

    public CommoditiesItemTypeListRequest(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
