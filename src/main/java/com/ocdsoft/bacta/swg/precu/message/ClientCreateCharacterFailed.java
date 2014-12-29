package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

/**
 * Created by crush on 8/14/2014.
 */
public class ClientCreateCharacterFailed extends SwgMessage {

    /**
     * Responses are found in the ui package starting with "name_declined"
     */

    public ClientCreateCharacterFailed(String name, String errorMessage) {
        super(0x3, 0xDF333C6E);

        writeUnicode(name);
        new StringId("ui", errorMessage).writeToBuffer(this);
    }
    //UnicodeString name
    //StringId errorMessage
}
