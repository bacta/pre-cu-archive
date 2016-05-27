package com.ocdsoft.bacta.swg.server.game.message.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.ocdsoft.bacta.soe.message.Priority;

import java.nio.ByteBuffer;

/**
      DE 00 00 00 01 00 00 00 

  SOECRC32.hashCode(SelectCharacter.class.getSimpleName()); // 0xb5098d76
  */
@Getter
@Priority(0x2)
@AllArgsConstructor
public final class SelectCharacter extends GameNetworkMessage {

    private final long characterId;

    public SelectCharacter(final ByteBuffer buffer) {
        this.characterId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(characterId);
    }
}
