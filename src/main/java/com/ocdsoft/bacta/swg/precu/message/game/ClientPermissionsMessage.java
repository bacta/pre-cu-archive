package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.message.Priority;
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
@Priority(0x4)
public final class ClientPermissionsMessage extends GameNetworkMessage {

    private final boolean canLogin;
    private final boolean canCreateCharacter;
    private final boolean unlimitedCharacterCreation;

    public ClientPermissionsMessage(final ByteBuffer buffer) {
        this.canLogin = BufferUtil.getBoolean(buffer);
        this.canCreateCharacter = BufferUtil.getBoolean(buffer);
        this.unlimitedCharacterCreation = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, canLogin);
        BufferUtil.putBoolean(buffer, canCreateCharacter);
        BufferUtil.putBoolean(buffer, unlimitedCharacterCreation);
    }
}
