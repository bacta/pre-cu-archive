package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

import java.nio.ByteBuffer;

/**
 * Created by crush on 8/14/2014.
 */
public class ClientCreateCharacterFailed extends GameNetworkMessage {

    /**
     * Responses are found in the ui package starting with "name_declined"
     */

    public ClientCreateCharacterFailed(String name, String errorMessage) {
        super(0x3, 0xDF333C6E);

        writeUnicode(name);
        new StringId("ui", errorMessage).writeToBuffer(this);
    }

    @Override
    public void readFromBuffer(ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
    //UnicodeString name
    //StringId errorMessage
}
