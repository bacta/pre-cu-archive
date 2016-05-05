package com.ocdsoft.bacta.swg.precu.message.game.client;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.localization.StringId;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/2/15.
 * 
 struct __cppobj ClientCreateCharacterFailed : GameNetworkMessage
 {
 Archive::AutoVariable<UnicodeString > m_name;
 Archive::AutoVariable<StringId> m_errorMessage;
 };
 */
@Priority(0x3)
public final class ClientCreateCharacterFailed extends GameNetworkMessage {

    private final String name;
    private final StringId errorMessage;

    /**
     * Responses are found in the ui package starting with "name_declined"
     */
    public ClientCreateCharacterFailed(final String name, final String errorKey) {
        this.name = name;
        this.errorMessage = new StringId("ui", errorKey);
    }

    public ClientCreateCharacterFailed(final ByteBuffer buffer) {
        this.name = BufferUtil.getAscii(buffer);
        this.errorMessage = new StringId(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, name);
        errorMessage.writeToBuffer(buffer);
    }
}
