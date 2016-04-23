package com.ocdsoft.bacta.swg.precu.message.game;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

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
public class ClientPermissionsMessage extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(ClientPermissionsMessage.class.getSimpleName());

    private boolean canLogin;
    private boolean canCreateRegularCharacter;
    private boolean canCreateJediCharacter;
    private boolean canSkipTutorial;

    @Inject
    public ClientPermissionsMessage() {
        super(priority, messageType);

        this.canLogin = false;
        this.canCreateRegularCharacter = false;
        this.canCreateJediCharacter = false;
        this.canSkipTutorial = false;
    }

    public ClientPermissionsMessage(boolean canLogin,
                                    boolean canCreateRegularCharacter,
                                    boolean canCreateJediCharacter,
                                    boolean canSkipTutorial) {
        super(priority, messageType);
        
        this.canLogin = canLogin;
        this.canCreateRegularCharacter = canCreateRegularCharacter;
        this.canCreateJediCharacter = canCreateJediCharacter;
        this.canSkipTutorial = canSkipTutorial;
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {
        this.canLogin = BufferUtil.getBoolean(buffer);
        this.canCreateRegularCharacter = BufferUtil.getBoolean(buffer);
        this.canCreateJediCharacter = BufferUtil.getBoolean(buffer);
        this.canSkipTutorial = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, canLogin);
        BufferUtil.putBoolean(buffer, canCreateRegularCharacter);
        BufferUtil.putBoolean(buffer, canCreateJediCharacter);
        BufferUtil.putBoolean(buffer, canSkipTutorial);
    }
}
