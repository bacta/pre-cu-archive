package com.ocdsoft.bacta.swg.server.game.message.creation;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.swg.shared.localization.StringId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by Kyle on 3/15/14.
 */

/**
 struct __cppobj ClientRandomNameResponse : GameNetworkMessage
 {
     Archive::AutoVariable<std::string > m_creatureTemplate;
     Archive::AutoVariable<UnicodeString > m_name;
     Archive::AutoVariable<StringId> m_errorMessage;
 }; 
 */
@Getter
@AllArgsConstructor
@Priority(0x4)
public final class ClientRandomNameResponse extends GameNetworkMessage {

    private final String creatureTemplate;
    private final String name;
    private final StringId errorMessage;

    public ClientRandomNameResponse(final ByteBuffer buffer) {
        creatureTemplate = BufferUtil.getAscii(buffer);
        name = BufferUtil.getUnicode(buffer);
        errorMessage = new StringId(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, creatureTemplate);
        BufferUtil.putUnicode(buffer, name);
        errorMessage.writeToBuffer(buffer);
    }
}
