package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.network.object.account.Account;

public class ClientPermissionsMessage extends SwgMessage {
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
}
