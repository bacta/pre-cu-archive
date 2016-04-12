package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.network.object.account.Account;

import java.nio.ByteBuffer;

public class ClientPermissionsMessage extends GameNetworkMessage {
    //bool canLogin
    //bool canCreateRegularCharacter
    //bool canCreateJediCharacter
    //bool canSkipTutorial

    public ClientPermissionsMessage(
            Account account,
            boolean canLogin,
            boolean canCreateRegularCharacter,
            boolean canCreateJediCharacter,
            boolean canSkipTutorial) {
        super(0x04, 0xE00730E5);
        
        writeBoolean(canLogin);
        writeBoolean(canCreateRegularCharacter);
        writeBoolean(canCreateJediCharacter);
        writeBoolean(canSkipTutorial);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
