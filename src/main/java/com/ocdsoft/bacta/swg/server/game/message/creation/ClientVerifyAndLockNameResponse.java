package com.ocdsoft.bacta.swg.server.game.message.creation;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**   {
 *      const std::string & creatureTemplate
 *    }
 *
      25 00 6F 62 6A 65 63 74 2F 63 72 65 61 74 75 72
    65 2F 70 6C 61 79 65 72 2F 68 75 6D 61 6E 5F 6D
    61 6C 65 2E 69 66 66 00 FF 7C 

  */
@Getter
@AllArgsConstructor
@Priority(0x3)
public final class ClientVerifyAndLockNameResponse extends GameNetworkMessage {

    private final String characterName;  // Unicode
    private final StringId errorMessage;

    public ClientVerifyAndLockNameResponse(final ByteBuffer buffer) {
        this.characterName = BufferUtil.getUnicode(buffer);  // Unicode
        this.errorMessage = new StringId(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putUnicode(buffer, characterName);
        errorMessage.writeToBuffer(buffer);
    }

}
