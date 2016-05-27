package com.ocdsoft.bacta.swg.server.game.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      

  SOECRC32.hashCode(CmdSceneReady.class.getSimpleName()); // 0x43fd1c22
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
