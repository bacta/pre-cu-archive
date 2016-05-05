package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;


/**
 * NGE Struct
 struct __cppobj ClientPermissionsMessage : GameNetworkMessage
 {
     Archive::AutoVariable<bool> m_canLogin;
     Archive::AutoVariable<bool> m_canCreateRegularCharacter;
     Archive::AutoVariable<bool> m_canCreateJediCharacter;
     Archive::AutoVariable<bool> m_canSkipTutorial;
 }; 
 */
@AllArgsConstructor
public final class ClientPermissionsMessage extends GameNetworkMessage {

    static {
        priority = 0x4;
        messageType = SOECRC32.hashCode(ClientPermissionsMessage.class.getSimpleName());
    }

    private final boolean canLogin;
    private final boolean canCreateRegularCharacter;
    private final boolean canCreateJediCharacter;
    private final boolean canSkipTutorial;

    public ClientPermissionsMessage(final ByteBuffer buffer) {
        this.canLogin = BufferUtil.getBoolean(buffer);
        this.canCreateRegularCharacter = BufferUtil.getBoolean(buffer);
        this.canCreateJediCharacter = BufferUtil.getBoolean(buffer);
        this.canSkipTutorial = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, canLogin);
        BufferUtil.putBoolean(buffer, canCreateRegularCharacter);
        BufferUtil.putBoolean(buffer, canCreateJediCharacter);
        BufferUtil.putBoolean(buffer, canSkipTutorial);
    }
}
