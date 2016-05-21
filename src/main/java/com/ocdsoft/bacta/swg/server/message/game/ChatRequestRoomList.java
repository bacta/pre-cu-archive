package com.ocdsoft.bacta.swg.server.message.game;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      

  SOECRC32.hashCode(ChatRequestRoomList.class.getSimpleName()); // 0x4c3d2cfa
  */
@Getter
@Priority(0x1)
public final class ChatRequestRoomList extends GameNetworkMessage {

    public ChatRequestRoomList() {

    }

    public ChatRequestRoomList(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
